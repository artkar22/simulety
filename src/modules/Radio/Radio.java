package modules.Radio;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalOutputImpl;
import ipsoConfig.ipsoInterfaces.ipsoDigitalOutput;
import modules.Simulet;

import java.awt.*;
import java.net.InetSocketAddress;


/**
 * Created by Artur Karolak on 2016-10-16.
 */

public class Radio extends IpsoDigitalOutputImpl {
    public static final boolean RADIO_SWITCHED_OFF = false;
    private InetSocketAddress simuletsAddress;

    public Radio(String nameOfSimulet, InetSocketAddress simuletsAddress) {
        super(nameOfSimulet);
        this.simuletsAddress=simuletsAddress;
        setState(RADIO_SWITCHED_OFF);
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

}

