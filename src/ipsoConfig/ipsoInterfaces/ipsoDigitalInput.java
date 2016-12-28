package ipsoConfig.ipsoInterfaces;

import static ipsoConfig.ipsoDefinitions.IPSO_DIGITAL_INPUT;

/**
 * Created by Inni on 2016-12-27.
 */
public interface ipsoDigitalInput {
    int id = IPSO_DIGITAL_INPUT;
//    boolean Digital_Output_State;

    int getId();

    boolean getState();

    void setState(boolean newState);
}
