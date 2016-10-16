package ipsoConfig.ipsoInterfaces.implementation;

import ipsoConfig.ipsoInterfaces.ipsoDigitalOutput;
import modules.Simulet;

import java.awt.*;
import java.net.InetSocketAddress;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class IpsoDigitalOutputImpl extends Simulet implements ipsoDigitalOutput {
    public static final boolean SWITCHED_ON = true;
    public static final boolean SWITCHED_OFF = false;
    private boolean Digital_Output_State;

    public IpsoDigitalOutputImpl(String nameOfSimulet) {
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
