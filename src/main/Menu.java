package main;

import configParser.ConfigParser;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import modules.Lampka.IpsoLightControl;
import modules.Lampka.resources.StatusResource;
import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.Trigger.BistableTrigger;
import modules.Trigger.TriggerMouseListener;
import modules.listOfAvailableModules;
import modules.resources.DigitalInputStateResource;
import modules.resources.DigitalOutputStateResource;
import modules.resources.IdResource;
import modules.resources.NameResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.server.resources.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.Enumeration;

import static ipsoConfig.ipsoDefinitions.*;
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
        startSimuletModule();
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

    private void createCOAPServer(final int id) {
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
            createCOAPServer(IPSO_LIGHT_CONTROL);
            ((IpsoLightControl) simulet).setSimuletsAddress(simuletsAddress);
//            LampkaActionListener listener = new LampkaActionListener((IpsoLightControl) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource(IPSO_LIGHT_CONTROL);
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
            createCOAPServer(IPSO_DIGITAL_OUTPUT);
//            WiatraczekActionListener listener = new WiatraczekActionListener((Wiatraczek) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource(IPSO_DIGITAL_OUTPUT);
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
            simulet = new BistableTrigger(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
            createCOAPServer(IPSO_DIGITAL_INPUT);
            ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
            IdResource idResource = new IdResource(IPSO_DIGITAL_INPUT, (BistableTrigger) this.simulet);
            NameResource nameResource = new NameResource(this.simulet.getNameOfSimulet());
            this.server.add(new Resource[]{idResource});
            this.server.add(new Resource[]{nameResource});
            DigitalInputStateResource on_off_Resource = new DigitalInputStateResource((BistableTrigger) simulet, this);
            on_off_Resource.setObservable(true);
            server.add(on_off_Resource);
            this.simulet.addMouseListener(new TriggerMouseListener((BistableTrigger) this.simulet, on_off_Resource, this));
            this.add(simulet);
            this.pack();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
