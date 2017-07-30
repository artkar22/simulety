package app;



import californium.core.CoapServer;
import californium.core.network.CoapEndpoint;
import californium.core.server.resources.Resource;
import configparser.ConfigParser;
import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import javafx.embed.swing.JFXPanel;
import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.Trigger.BistableTrigger;
import modules.Trigger.CounterTriggerMouseListener;
import modules.Trigger.TriggerMouseListener;
import modules.listOfAvailableModules;
import modules.resources.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.Enumeration;

import org.apache.mina.util.AvailablePortFinder;

import static ipsoconfig.IpsoDefinitions.IPSO_DIGITAL_INPUT;
import static ipsoconfig.IpsoDefinitions.IPSO_DIGITAL_OUTPUT;
import static modules.listOfAvailableModules.COUNTER_SIMULET;
import static modules.listOfAvailableModules.EVENT_SIMULET;


public class Menu extends JFrame implements ActionListener {


    private final String nameOfSimulet;
    private int serverPort;
    private String className;
    private Simulet simulet;
    private californium.core.CoapServer server;
    private InetSocketAddress simuletsAddress;
    private String initialStateName;
    private PossibleStatesListWrapper possibleStates;

    public Menu(final String nameOfSimulet) {
        this.nameOfSimulet = nameOfSimulet;
        final JFXPanel fxPanel = new JFXPanel();
        configurateWindow();
        loadConfiguration();
        startModule();
        server.start();
    }

    private void configurateWindow() {
        this.setBounds(0, 0, 1024, 768);
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(nameOfSimulet);
        this.setVisible(true);
    }

    private void loadConfiguration() {
        final ConfigParser parser = new ConfigParser(nameOfSimulet);
        parser.parse();
        serverPort = getServerPort(parser.getPort());
        className = parser.getClassName();
        initialStateName = parser.getInitialStateName();
        possibleStates = new PossibleStatesListWrapper(parser.getPossibleStates());

    }
    private int getServerPort(int portFromConfig) {
        if(portFromConfig != 0 && AvailablePortFinder.available(portFromConfig)){
            return portFromConfig;
        } else {
            while(!AvailablePortFinder.available(portFromConfig)){
                portFromConfig++;
            }
            return portFromConfig;
        }
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

    private void startModule() {
        if (listOfAvailableModules.ACTION_SIMULET.equals(className)) {
            startSimuletModule();
        } else if (EVENT_SIMULET.equals(className)) {
            startTriggerModule();
        } else if(COUNTER_SIMULET.equals(className)) {
            startCounterTriggerModule();
        }
        final PossibleStatesResource possibleStatesResource = new PossibleStatesResource(possibleStates, className);
        server.add(possibleStatesResource);
    }

    private void startSimuletModule() {
        simulet = new IpsoDigitalOutputImpl(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
        createCOAPServer(IPSO_DIGITAL_OUTPUT);
        this.add(simulet);
        this.pack();
        IdResource idResource = new IdResource(IPSO_DIGITAL_OUTPUT);
        server.add(idResource);
        CurrentStateResource on_off_Resource = new CurrentStateResource((IpsoDigitalOutputImpl) simulet, this);
        server.add(on_off_Resource);
    }

    private void startCounterTriggerModule() {
        simulet = new BistableTrigger(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
        createCOAPServer(IPSO_DIGITAL_INPUT);
        ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
        IdResource idResource = new IdResource(IPSO_DIGITAL_INPUT, (BistableTrigger) this.simulet);
//            NameResource nameResource = new NameResource(this.simulet.getNameOfSimulet());
        this.server.add(new Resource[]{idResource});
//            this.server.add(new Resource[]{nameResource});
        ObservableCurrentStateResource on_off_Resource = new ObservableCurrentStateResource((BistableTrigger) simulet, this);
        on_off_Resource.setObservable(true);
        server.add(on_off_Resource);
        this.simulet.addMouseListener(new CounterTriggerMouseListener((BistableTrigger) this.simulet, on_off_Resource, this));
        this.add(simulet);
        this.pack();
    }

    private void startTriggerModule() {
            simulet = new BistableTrigger(nameOfSimulet, className, possibleStates.getStateById(initialStateName), possibleStates);
            createCOAPServer(IPSO_DIGITAL_INPUT);
            ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
            IdResource idResource = new IdResource(IPSO_DIGITAL_INPUT, (BistableTrigger) this.simulet);
//            NameResource nameResource = new NameResource(this.simulet.getNameOfSimulet());
            this.server.add(new Resource[]{idResource});
//            this.server.add(new Resource[]{nameResource});
            ObservableCurrentStateResource on_off_Resource = new ObservableCurrentStateResource((BistableTrigger) simulet, this);
            on_off_Resource.setObservable(true);
            server.add(on_off_Resource);
            this.simulet.addMouseListener(new TriggerMouseListener((BistableTrigger) this.simulet, on_off_Resource, this));
            this.add(simulet);
            this.pack();
    }


    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
