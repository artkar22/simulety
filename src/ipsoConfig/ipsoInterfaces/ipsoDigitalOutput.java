package ipsoConfig.ipsoInterfaces;

import static ipsoConfig.ipsoDefinitions.IPSO_DIGITAL_OUTPUT;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public interface ipsoDigitalOutput {
    int id = IPSO_DIGITAL_OUTPUT;
//    boolean Digital_Output_State;

    int getId();

    boolean getState();

    void setState(boolean newState);
}
