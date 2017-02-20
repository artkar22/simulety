package ipsoConfig.ipsoInterfaces.implementation;

import ipsoConfig.ipsoInterfaces.ipsoDigitalInput;
import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.SimuletsState;

import java.awt.*;
import java.net.InetAddress;

/**
 * Created by Inni on 2016-12-27.
 */
public class IpsoDigitalInputImpl extends Simulet implements ipsoDigitalInput {

    private InetAddress mobileAppAddress;
    private int mobileAppPort;

    public IpsoDigitalInputImpl(final String nameOfSimulet, final String className, final SimuletsState currentState, final PossibleStatesListWrapper possibleStates) {
        super(nameOfSimulet, className, currentState, possibleStates);
    }

    public int getMobileAppPort() {
        return mobileAppPort;
    }

    public void setMobileAppPort(int mobileAppPort) {
        this.mobileAppPort = mobileAppPort;
    }

    public InetAddress getMobileAppAddress() {
        return mobileAppAddress;
    }

    public void setMobileAppAddress(InetAddress mobileAppAddress) {
        this.mobileAppAddress = mobileAppAddress;
    }

    @Override
    public int getId() {
        return id;
    }

}
