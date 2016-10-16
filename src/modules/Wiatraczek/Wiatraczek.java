package modules.Wiatraczek;

import java.awt.Dimension;
import java.net.InetSocketAddress;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import ipsoConfig.ipsoInterfaces.ipsoDigitalOutput;
import modules.Simulet;


/**
 * Created by Artur Karolak on 2016-10-16.
 */

public class Wiatraczek extends IpsoDigitalOutputImpl {
    public static final boolean FAN_SWITCHED_OFF = false;
    private InetSocketAddress simuletsAddress;

    public Wiatraczek(String nameOfSimulet, InetSocketAddress simuletsAddress) {
        super(nameOfSimulet);
        this.simuletsAddress=simuletsAddress;
        setState(FAN_SWITCHED_OFF);
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

}

