package modules.Trigger;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;

import java.net.InetSocketAddress;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class Trigger extends IpsoDigitalInputImpl {
    public static final boolean TRIGGER_SWITCHED_OFF = false;
    public static final boolean TRIGGER_SWITCHED_ON = true;

    private InetSocketAddress simuletsAddress;

    public Trigger(String nameOfSimulet) {
        super(nameOfSimulet);
        setState(TRIGGER_SWITCHED_OFF);
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

    public void setSimuletsAddress(final InetSocketAddress simuletsAddress) {
        this.simuletsAddress = simuletsAddress;
    }

}