package modules.Lampka;

import java.awt.Dimension;
import java.net.InetSocketAddress;

import ipsoConfig.ipsoInterfaces.ipsoLightControl;
import modules.Lampka.resources.StatusResource;
import modules.Simulet;

import static ipsoConfig.ipsoDefinitions.IPSO_LIGHT_CONTROL;

public class IpsoLightControlImpl extends Simulet implements ipsoLightControl {


    private InetSocketAddress simuletsAddress;
    private boolean on_off;
    public static final boolean LAMP_SWITCHED_ON = true;
    public static final boolean LAMP_SWITCHED_OFF = false;


    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

    public IpsoLightControlImpl(String nameOfSimulet) {
        super(nameOfSimulet);
        setStatus(LAMP_SWITCHED_OFF);
    }



    public void switchOn() {
        setStatus(LAMP_SWITCHED_ON);
        currentImage = images.get(1);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public void switchOff() {
        setStatus(LAMP_SWITCHED_OFF);
        currentImage = images.get(0);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public int getId() {
        return id;
    }

    public boolean getOn_Off_Status() {
        return on_off;
    }

    private void setStatus(boolean newStatus) {
        on_off = newStatus;
    }

    public void setSimuletsAddress(InetSocketAddress simuletsAddress) {
        this.simuletsAddress = simuletsAddress;
    }
}