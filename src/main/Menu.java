package main;

import configParser.ConfigParser;
import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import modules.Lampka.IpsoLightControlImpl;
import modules.Lampka.resources.StatusResource;
import modules.Simulet;
import modules.Trigger.Trigger;
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
import java.awt.image.BufferedImage;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import static modules.listOfAvailableModules.TRIGGER;


public class Menu extends JFrame implements ActionListener {


    private final String nameOfSimulet;
    private int serverPort;
    private String className;
    private ArrayList<BufferedImage> images;
    private JButton button;
    private Simulet simulet;
    //    private Budzik budzik;
    private CoapServer server;
    private InetSocketAddress simuletsAddress;

    public Menu(final String nameOfSimulet) {
        this.nameOfSimulet = nameOfSimulet;
        configurateWindow();
        loadConfiguration();
        if (TRIGGER.equals(className)) {
            button = new JButton("testButton");
            this.add(button);
        }
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
        ConfigParser parser = new ConfigParser(nameOfSimulet);
        parser.Parse();
        serverPort = parser.getPort();
        className = parser.getClassName();
    }

    private void createCOAPServer(int id) {
        InetAddress addr;
        try {
            addr = InetAddress.getByName(getIPofCurrentMachine());
            simuletsAddress = new InetSocketAddress(addr, serverPort);
            CoapEndpoint endpoint = new CoapEndpoint(simuletsAddress);
            server = new CoapServer(Integer.toString(id));
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

            simulet = new IpsoLightControlImpl(nameOfSimulet);
            createCOAPServer(((IpsoLightControlImpl) simulet).getId());
            ((IpsoLightControlImpl) simulet).setSimuletsAddress(simuletsAddress);
//            LampkaActionListener listener = new LampkaActionListener((IpsoLightControlImpl) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((IpsoLightControlImpl) simulet);
            server.add(idResource);
            StatusResource on_off_Resource = new StatusResource((IpsoLightControlImpl) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (listOfAvailableModules.IpsoDigitalOutput.equals(className)
            //nameOfSimulet.equals(listOfAvailableModules.WIATRACZEK) || nameOfSimulet.equals(listOfAvailableModules.RADIO) || nameOfSimulet.equals(listOfAvailableModules.SAMOCHOD)
                ) {

            simulet = new IpsoDigitalOutputImpl(nameOfSimulet, simuletsAddress);
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
        } else if (TRIGGER.equals(className)) {
            startTriggerModule();
        }
//        else if (nameOfSimulet.equals(listOfAvailableModules.RADIO)) {
//
//            simulet = new Radio(nameOfSimulet, simuletsAddress);
//            createCOAPServer(((Radio) simulet).getId());
////            RadioActionListener listener = new RadioActionListener((Radio) simulet, this);
////            button.addActionListener(listener);
//            this.add(simulet);
//            this.pack();
//            IdResource idResource = new IdResource((Radio) simulet);
//            server.add(idResource);
//            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((Radio) simulet, this);
//            server.add(on_off_Resource);
//            //images = lampka.getImages();
////            LampkaResource lampkaResource = new LampkaResource(lampka, this);
////            server.add(lampkaResource);
//        } else if (nameOfSimulet.equals(listOfAvailableModules.SAMOCHOD)) {
//
//            simulet = new Samochod(nameOfSimulet, simuletsAddress);
//            createCOAPServer(((Samochod) simulet).getId());
////            TriggerActionListener listener = new TriggerActionListener((Samochod) simulet, this);
////            button.addActionListener(listener);
//            this.add(simulet);
//            this.pack();
//            IdResource idResource = new IdResource((Samochod) simulet);
//            server.add(idResource);
//            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((Samochod) simulet, this);
//            server.add(on_off_Resource);
//            //images = lampka.getImages();
////            LampkaResource lampkaResource = new LampkaResource(lampka, this);
////            server.add(lampkaResource);
//        }
    }

    private void startTriggerModule() {
        if (nameOfSimulet.equals(listOfAvailableModules.TRIGGER_1) ||
                nameOfSimulet.equals(listOfAvailableModules.TRIGGER_2)) {
            simulet = new Trigger(nameOfSimulet);
            createCOAPServer(((Trigger) simulet).getId());
            ((Trigger) simulet).setSimuletsAddress(simuletsAddress);
            IdResource idResource = new IdResource((Trigger) simulet);
            server.add(idResource);
            DigitalInputStateResource on_off_Resource = new DigitalInputStateResource((Trigger) simulet, this);
            on_off_Resource.setObservable(true);
            server.add(on_off_Resource);
            TriggerActionListener listener = new TriggerActionListener((Trigger) simulet, on_off_Resource, this);
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
