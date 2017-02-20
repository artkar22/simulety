package modules.Lampka;

import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.SimuletsState;

import java.net.InetSocketAddress;

public class IpsoLightControl extends Simulet {

    public static final String LAMP_SWITCHED_ON = "ON";
    public static final String LAMP_SWITCHED_OFF = "OFF";
    private InetSocketAddress simuletsAddress;


    public IpsoLightControl(final String nameOfSimulet, final String className, final SimuletsState currentState, final PossibleStatesListWrapper possibleStates) {
        super(nameOfSimulet, className, currentState, possibleStates);
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

    public void setSimuletsAddress(InetSocketAddress simuletsAddress) {
        this.simuletsAddress = simuletsAddress;
    }

    public void switchOn() {
        this.setCurrentState(possibleStates.getStateById(LAMP_SWITCHED_ON));
        setCurrentImage();
    }

    public void switchOff() {
        this.setCurrentState(possibleStates.getStateById(LAMP_SWITCHED_OFF));
        setCurrentImage();
    }

}
