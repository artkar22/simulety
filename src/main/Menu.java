package main;

import configParser.ConfigParser;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import modules.Lampka.IpsoLightControl;
import modules.Lampka.resources.StatusResource;
import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.Trigger.BistableTrigger;
import modules.Trigger.TriggerActionListener;
import modules.listOfAvailableModules;
import modules.resources.DigitalInputStateResource;
import modules.resources.DigitalOutputStateResource;
import modules.resources.IdResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static modules.listOfAvailableModules.BISTABLE_TRIGGER;


public class Menu extends JFrame implements ActionListener {


    private final String nameOfSimulet;
    private int serverPort;
    private String className;
    private JButton button;
    private Simulet simulet;
    private CoapServer server;
    private InetSocketAddress simuletsAddress;
    private String initialStateName;
    private PossibleStatesListWrapper possibleStates;

    public Menu(final String nameOfSimulet) {
        this.nameOfSimulet = nameOfSimulet;
        configurateWindow();
        loadConfiguration();
        if (BISTABLE_TRIGGER.equals(className)) {
            button = new JButton("testButton");
            this.add(button);
        }
        server.start();
    }

    private void configurateWindow() {
//        button = new JButton("testButton");
        this.setBounds(0, 0, 1024, 768);
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(nameOfSimulet);
        this.setVisible(true);
    }

    private void loadConfiguration() {
        final ConfigParser parser = new ConfigParser(nameOfSimulet);
        parser.Parse();
        serverPort = parser.getPort();
        className = parser.getClassName();
        initialStateName = parser.getInitialStateName();
        possibleStates = new PossibleStatesListWrapper(parser.getPossibleStates());
    }

    private void createCOAPServer(String id) {
        InetAddress addr;
        try {
            addr = InetAddress.getByName(getIPofCurrentMachine());
            simuletsAddress = new InetSocketAddress(addr, serverPort);
            CoapEndpoint endpoint = new CoapEndpoint(simuletsAddress);
            server = new CoapServer(id);
            server.addEndpoint(endpoint);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String getIPofCurrentMachine() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp() || iface.isVirtual() || iface.isPointToPoint())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    final String ip = addr.getHostAddress();
                    if (Inet4Address.class == addr.getClass()) return ip;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void startSimuletModule() {
        if (listOfAvailableModules.LAMPKA.equals(className)) {

            simulet = new IpsoLightControl(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
            createCOAPServer(simulet.getNameOfSimulet());
            ((IpsoLightControl) simulet).setSimuletsAddress(simuletsAddress);
//            LampkaActionListener listener = new LampkaActionListener((IpsoLightControl) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((IpsoLightControl) simulet);
            server.add(idResource);
            StatusResource on_off_Resource = new StatusResource((IpsoLightControl) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (listOfAvailableModules.IpsoDigitalOutput.equals(className)
            //nameOfSimulet.equals(listOfAvailableModules.WIATRACZEK) || nameOfSimulet.equals(listOfAvailableModules.RADIO) || nameOfSimulet.equals(listOfAvailableModules.SAMOCHOD)
                ) {

            simulet = new IpsoDigitalOutputImpl(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
            createCOAPServer(((IpsoDigitalOutputImpl) simulet).getId());
//            WiatraczekActionListener listener = new WiatraczekActionListener((Wiatraczek) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((IpsoDigitalOutputImpl) simulet);
            server.add(idResource);
            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((IpsoDigitalOutputImpl) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (BISTABLE_TRIGGER.equals(className)) {
            startTriggerModule();
        }
    }

    private void startTriggerModule() {
        if (nameOfSimulet.equals(listOfAvailableModules.TRIGGER_1) ||
                nameOfSimulet.equals(listOfAvailableModules.TRIGGER_2)) {
            simulet = new BistableTrigger(nameOfSimulet,className, possibleStates.getStateById(initialStateName), possibleStates);
            createCOAPServer(((BistableTrigger) simulet).getId());
            ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
            IdResource idResource = new IdResource((BistableTrigger) simulet);
            server.add(idResource);
            DigitalInputStateResource on_off_Resource = new DigitalInputStateResource((BistableTrigger) simulet, this);
            on_off_Resource.setObservable(true);
            server.add(on_off_Resource);
            TriggerActionListener listener = new TriggerActionListener( on_off_Resource);
            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
