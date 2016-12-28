package ipsoConfig.ipsoInterfaces.implementation;

import ipsoConfig.ipsoInterfaces.ipsoDigitalInput;
import modules.Simulet;

import java.awt.*;
import java.net.InetAddress;

/**
 * Created by Inni on 2016-12-27.
 */
public class IpsoDigitalInputImpl extends Simulet implements ipsoDigitalInput {

    public static final boolean SWITCHED_ON = true;
    public static final boolean SWITCHED_OFF = false;
    private boolean Digital_Output_State;

    private InetAddress mobileAppAddress;
    private int mobileAppPort;

    public IpsoDigitalInputImpl(String nameOfSimulet) {
        super(nameOfSimulet);
    }

    public void switchOn() {
        setState(SWITCHED_ON);
        currentImage = images.get(1);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public void switchOff() {
        setState(SWITCHED_OFF);
        currentImage = images.get(0);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public void setMobileAppAddress(InetAddress mobileAppAddress) {
        this.mobileAppAddress = mobileAppAddress;
    }
    public void setMobileAppPort(int mobileAppPort) {
        this.mobileAppPort = mobileAppPort;
    }

    public int getMobileAppPort() {
        return mobileAppPort;
    }

    public InetAddress getMobileAppAddress() {
        return mobileAppAddress;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean getState() {
        return Digital_Output_State;
    }

    @Override
    public void setState(boolean newState) {
        this.Digital_Output_State = newState;
    }

}
