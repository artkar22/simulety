package modules.Trigger;

import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import modules.PossibleStatesListWrapper;
import modules.SimuletsState;

import java.net.InetSocketAddress;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class BistableTrigger extends IpsoDigitalInputImpl {
    public static final String TRIGGER_SWITCHED_OFF = "OFF";
    public static final String TRIGGER_SWITCHED_ON = "ON";

    private InetSocketAddress simuletsAddress;

    public BistableTrigger(String nameOfSimulet, final String className, final SimuletsState currentState, final PossibleStatesListWrapper possibleStates) {
        super(nameOfSimulet, className, currentState, possibleStates);
    }

    public void switchOn() {
        this.setCurrentState(possibleStates.getStateById(TRIGGER_SWITCHED_ON));
        setCurrentImage();
    }

    public void switchOff() {
        this.setCurrentState(possibleStates.getStateById(TRIGGER_SWITCHED_OFF));
        setCurrentImage();
    }

    public InetSocketAddress getSimuletsAddress() {
        return simuletsAddress;
    }

    public void setSimuletsAddress(final InetSocketAddress simuletsAddress) {
        this.simuletsAddress = simuletsAddress;
    }

}