//package modules.Wiatraczek;
//
//import java.awt.Dimension;
//import java.net.InetSocketAddress;
//
//import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
//import ipsoconfig.ipsoInterfaces.ipsoDigitalOutput;
//import modules.Simulet;
//
//
///**
// * Created by Artur Karolak on 2016-10-16.
// */
//
//public class Wiatraczek extends IpsoDigitalOutputImpl {
//    public static final boolean FAN_SWITCHED_OFF = false;
//    private InetSocketAddress simuletsAddress;
//
//    public Wiatraczek(String nameOfSimulet, InetSocketAddress simuletsAddress) {
//        super(nameOfSimulet,simuletsAddress);
//        this.simuletsAddress=simuletsAddress;
//        setState(FAN_SWITCHED_OFF);
//    }
//
//    public InetSocketAddress getSimuletsAddress() {
//        return simuletsAddress;
//    }
//
//}
//
