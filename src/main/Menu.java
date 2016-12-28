package main;

import configParser.ConfigParser;
import modules.Lampka.Lampka;
import modules.Lampka.resources.StatusResource;
import modules.Radio.Radio;
import modules.Samochod.Samochod;
import modules.Simulet;
import modules.Trigger.Trigger;
import modules.Trigger.TriggerActionListener;
import modules.Wiatraczek.Wiatraczek;
import modules.listOfAvailableModules;
import modules.listOfAvailableModulesTypes;
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
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

import static modules.listOfAvailableModulesTypes.TRIGGER;

public class Menu extends JFrame implements ActionListener {


    private final String nameOfSimulet;
    private final String simuletsType;
    private int serverPort;
    private ArrayList<BufferedImage> images;
    private JButton button;
    private Simulet simulet;
    //    private Budzik budzik;
    private CoapServer server;
    private InetSocketAddress simuletsAddress;

    public Menu(final String nameOfSimulet, final String simuletsType) {
        this.nameOfSimulet = nameOfSimulet;
        this.simuletsType = simuletsType;
        configurateWindow();
        loadConfiguration();
        startModule();
        server.start();
    }

    private void configurateWindow() {
//        button = new JButton("testButton");
        this.setBounds(0, 0, 1024, 768);
        this.setLayout(new GridLayout());
        if (TRIGGER.equals(simuletsType)) {
            button = new JButton("testButton");
            this.add(button);
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(nameOfSimulet);
        this.setVisible(true);
    }

    private void loadConfiguration() {
        ConfigParser parser = new ConfigParser(nameOfSimulet);
        parser.Parse();
        serverPort = parser.getPort();
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

    private void startModule() {
        if (listOfAvailableModulesTypes.SIMULET.equals(simuletsType)) {
            startSimuletModule();
        } else {
            startTriggerModule();
        }
    }

    private void startSimuletModule() {
        if (nameOfSimulet.equals(listOfAvailableModules.LAMPKA)) {

            simulet = new Lampka(nameOfSimulet);
            createCOAPServer(((Lampka) simulet).getId());
            ((Lampka)simulet).setSimuletsAddress(simuletsAddress);
//            LampkaActionListener listener = new LampkaActionListener((Lampka) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((Lampka) simulet);
            server.add(idResource);
            StatusResource on_off_Resource = new StatusResource((Lampka) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (nameOfSimulet.equals(listOfAvailableModules.WIATRACZEK)) {

            simulet = new Wiatraczek(nameOfSimulet, simuletsAddress);
            createCOAPServer(((Wiatraczek) simulet).getId());
//            WiatraczekActionListener listener = new WiatraczekActionListener((Wiatraczek) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((Wiatraczek) simulet);
            server.add(idResource);
            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((Wiatraczek) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (nameOfSimulet.equals(listOfAvailableModules.RADIO)) {

            simulet = new Radio(nameOfSimulet, simuletsAddress);
            createCOAPServer(((Radio) simulet).getId());
//            RadioActionListener listener = new RadioActionListener((Radio) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((Radio) simulet);
            server.add(idResource);
            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((Radio) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        } else if (nameOfSimulet.equals(listOfAvailableModules.SAMOCHOD)) {

            simulet = new Samochod(nameOfSimulet, simuletsAddress);
            createCOAPServer(((Samochod) simulet).getId());
//            TriggerActionListener listener = new TriggerActionListener((Samochod) simulet, this);
//            button.addActionListener(listener);
            this.add(simulet);
            this.pack();
            IdResource idResource = new IdResource((Samochod) simulet);
            server.add(idResource);
            DigitalOutputStateResource on_off_Resource = new DigitalOutputStateResource((Samochod) simulet, this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        }
    }

    private void startTriggerModule() {
        if (nameOfSimulet.equals(listOfAvailableModules.TRIGGER_1) ||
                nameOfSimulet.equals(listOfAvailableModules.TRIGGER_2)) {
            simulet = new Trigger(nameOfSimulet);
            createCOAPServer(((Trigger) simulet).getId());
            ((Trigger)simulet).setSimuletsAddress(simuletsAddress);
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
