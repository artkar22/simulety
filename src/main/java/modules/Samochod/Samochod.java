//package modules.Samochod;
//
//import main.java.ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
//
//import java.net.InetSocketAddress;
//
///**
// * Created by Artur Karolak on 2016-10-16.
// */
//public class Samochod extends IpsoDigitalOutputImpl {
//    public static final boolean SAMOCHOD_SWITCHED_OFF = false;
//    private InetSocketAddress simuletsAddress;
//
//    public Samochod(String nameOfSimulet, InetSocketAddress simuletsAddress) {
//        super(nameOfSimulet, simuletsAddress);
//        this.simuletsAddress = simuletsAddress;
//        setState(SAMOCHOD_SWITCHED_OFF);
//    }
//
//    public InetSocketAddress getSimuletsAddress() {
//        return simuletsAddress;
//    }
//}