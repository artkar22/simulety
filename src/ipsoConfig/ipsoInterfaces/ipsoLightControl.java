package ipsoConfig.ipsoInterfaces;

import modules.Simulet;

import static ipsoConfig.ipsoDefinitions.IPSO_LIGHT_CONTROL;
import static ipsoConfig.ipsoUtilities.WHITE;

/**
 * Created by Artur Karolak on 2016-05-17.
 */
public interface ipsoLightControl {
    // int id = IPSO_LIGHT_CONTROL;
    //boolean on_off = false;
//    int dimmer =0;
//    static final int MAX_DIMMER = 100;
//    static final int MIN_DIMMER = 0;
   // protected String colour = WHITE;
    int id = IPSO_LIGHT_CONTROL;
    int getId();
    boolean getOn_Off_Status();



}
