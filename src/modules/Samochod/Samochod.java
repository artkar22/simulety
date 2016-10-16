package modules.Samochod;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;

import java.net.InetSocketAddress;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class Samochod extends IpsoDigitalOutputImpl {
    public static final boolean SAMOCHOD_SWITCHED_OFF = false;
    private InetSocketAddress simuletsAddress;

    public Samochod(String nameOfSimulet, InetSocketAddress simuletsAddress) {
        super(nameOfSimulet);
        this.simuletsAddress = simuletsAddress;
        setState(SAMOCHOD_SWITCHED_OFF);
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }
}