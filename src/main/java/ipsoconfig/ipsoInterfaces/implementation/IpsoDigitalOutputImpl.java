package ipsoconfig.ipsoInterfaces.implementation;

import modules.PossibleStatesListWrapper;
import modules.Simulet;
import modules.SimuletsState;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class IpsoDigitalOutputImpl extends Simulet {
    public static final String SWITCHED_ON = "ON";
    public static final String SWITCHED_OFF = "OFF";

    public IpsoDigitalOutputImpl(final String nameOfSimulet, final String className, final SimuletsState currentState, final PossibleStatesListWrapper possibleStates) {
        super(nameOfSimulet, className, currentState, possibleStates);
    }

    public void switchOn() {
        this.setCurrentState(possibleStates.getStateById(SWITCHED_ON));
        setCurrentImage();
    }

    public void switchOff() {
        this.setCurrentState(possibleStates.getStateById(SWITCHED_OFF));
        setCurrentImage();
    }

}
