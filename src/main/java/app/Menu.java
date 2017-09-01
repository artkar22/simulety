package app;



import californium.core.CoapServer;
import californium.core.network.CoapEndpoint;
import configparser.ConfigParser;
import javafx.embed.swing.JFXPanel;
import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.SimuletsState;
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
    private SimuletsState initialState;
    private String timeToResetToInitial;

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
        initialState = parser.getInitialState();
        timeToResetToInitial = parser.getTimeToResetToInitial();

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

    private void createCOAPServer(final String id) {
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

    private SimuletsState getInitialStateForModule(){
        if(initialState != null){
            return initialState;
        } else {
            return possibleStates.getStateById(initialStateName);
        }
    }
    private void startSimuletModule() {

        simulet = new Simulet(nameOfSimulet, className, getInitialStateForModule(), possibleStates);
        createCOAPServer(className);
        this.add(simulet);
        this.pack();
        IdResource idResource = new IdResource(this.simulet.getNameOfSimulet());
        ClassResource classResource = new ClassResource(this.simulet.getClassName());
        server.add(idResource);
        server.add(classResource);
        CurrentStateResource on_off_Resource = new CurrentStateResource(simulet, timeToResetToInitial, this);
        server.add(on_off_Resource);
    }

    private void startCounterTriggerModule() {
        simulet = new BistableTrigger(nameOfSimulet, className, getInitialStateForModule(), possibleStates);
        createCOAPServer(className);
        ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
        IdResource idResource = new IdResource(this.simulet.getNameOfSimulet());
        ClassResource classResource = new ClassResource(this.simulet.getClassName());
        this.server.add(idResource);
        this.server.add(classResource);
        CounterSimuletCurrentStateResource on_off_Resource = new CounterSimuletCurrentStateResource((BistableTrigger) simulet, this, initialState);
        on_off_Resource.setObservable(true);
        server.add(on_off_Resource);

        JPanel panel = new JPanel(new GridLayout(8,8,8,8));
        JButton ten = new JButton("10");
        JButton five = new JButton("5");
        JButton three = new JButton("3");
        ten.addMouseListener(new CounterTriggerMouseListener(10, (BistableTrigger) simulet, possibleStates, nameOfSimulet, this));
        five.addMouseListener(new CounterTriggerMouseListener(5, (BistableTrigger) simulet, possibleStates, nameOfSimulet, this));
        three.addMouseListener(new CounterTriggerMouseListener(3, (BistableTrigger) simulet, possibleStates, nameOfSimulet, this));
        ten.setPreferredSize(new Dimension(10, 10));
        five.setPreferredSize(new Dimension(10, 10));
        three.setPreferredSize(new Dimension(10, 10));
        panel.add(ten);
        panel.add(five);
        panel.add(three);
        panel.setPreferredSize(new Dimension(30,30));
        this.add(panel);
//        this.simulet.addMouseListener(new CounterTriggerMouseListener((BistableTrigger) this.simulet, on_off_Resource, this));
        this.add(simulet);
        this.pack();
    }

    private void startTriggerModule() {
            simulet = new BistableTrigger(nameOfSimulet, className, getInitialStateForModule(), possibleStates);
            createCOAPServer(className);
            ((BistableTrigger) simulet).setSimuletsAddress(simuletsAddress);
            IdResource idResource = new IdResource(this.simulet.getNameOfSimulet());
            ClassResource classResource = new ClassResource(this.simulet.getClassName());
            this.server.add(idResource);
            this.server.add(classResource);
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


    public SimuletsState getInitialState() {
        return initialState;
    }

}
