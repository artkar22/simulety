package main;

import java.awt.GridLayout;
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

import javax.swing.JButton;
import javax.swing.JFrame;

import modules.Lampka.resources.StatusResource;
import modules.listOfAvailableModules;
import modules.Budzik.Budzik;
import modules.Lampka.Lampka;

import modules.resources.IdResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;

import serverResources.BudzikResource;
import configParser.ConfigParser;

public class Menu extends JFrame implements ActionListener {

    private String nameOfSimulet;
    private int serverPort;
    private ArrayList<BufferedImage> images;
    private JButton button;
    private Lampka lampka;
    private Budzik budzik;
    private CoapServer server;
    private InetSocketAddress simuletsAddress;

    public Menu(String nameOfSimulet) {
        this.nameOfSimulet = nameOfSimulet;
        configurateWindow();
        loadConfiguration();
        startModule();
        server.start();
    }

    private void configurateWindow() {
//		button = new JButton("testButton");
        this.setBounds(0, 0, 1024, 768);
        this.setLayout(new GridLayout());
//		this.add(button);
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
        if (nameOfSimulet.equals(listOfAvailableModules.LAMPKA)) {

            lampka = new Lampka(nameOfSimulet,simuletsAddress);
            createCOAPServer(lampka.getId());
//			LampkaActionListener listener = new LampkaActionListener(lampka,this);
//			button.addActionListener(listener);
            this.add(lampka);
            this.pack();
            IdResource idResource = new IdResource(lampka);
            server.add(idResource);
            StatusResource on_off_Resource = new StatusResource(lampka,this);
            server.add(on_off_Resource);
            //images = lampka.getImages();
//            LampkaResource lampkaResource = new LampkaResource(lampka, this);
//            server.add(lampkaResource);
        }
        if (nameOfSimulet.equals(listOfAvailableModules.BUDZIK)) {

            budzik = new Budzik(nameOfSimulet);
            createCOAPServer(budzik.getId());
            this.add(budzik);
            this.pack();
            //images = lampka.getImages();
            BudzikResource budzikResource = new BudzikResource(budzik, this);
            server.add(budzikResource);
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
