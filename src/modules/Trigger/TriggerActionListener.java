package modules.Trigger;

import ipsoConfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import modules.resources.DigitalInputStateResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static modules.listOfAvailableModules.BISTABLE_TRIGGER;

/**
 * Created by Artur Karolak on 2016-10-16.
 */
public class TriggerActionListener implements ActionListener {
    private DigitalInputStateResource on_off_resource;

    public TriggerActionListener(final DigitalInputStateResource on_off_resource) {
        this.on_off_resource = on_off_resource;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        sendTriggerChange();
//        bistableTrigger.switchOn();
//        menu.repaint();
    }

    private void sendTriggerChange() {
        on_off_resource.setButtonActionFlagTrue();
        final IpsoDigitalInputImpl digitalInput = on_off_resource.getDigitalInput();
        if (BISTABLE_TRIGGER.equals(digitalInput.getClassName())) {
            if (BistableTrigger.TRIGGER_SWITCHED_ON.equals(digitalInput.getCurrentState().getStateId())) {
                digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_OFF));
            } else if (BistableTrigger.TRIGGER_SWITCHED_OFF.equals(digitalInput.getCurrentState().getStateId())) {
                digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_ON));
            }
        }
//        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
//            digitalInput.setState(digitalInput.SWITCHED_OFF);
//        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
//            digitalInput.setState(digitalInput.SWITCHED_ON);
//        }
        on_off_resource.changed();
        on_off_resource.setButtonActionFlagFalse();
//        server.add(on_off_Resource);
//        server.get
//        if (bistableTrigger.getMobileAppAddress() != null) {
//            final Endpoint myEndpoint = server.getEndpoint(bistableTrigger.getSimuletsAddress());
//            myEndpoint.
//            myEndpoint.sendRequest(request());
//            myEndpoint.
//        }
    }

//    private Request request() {
//        final Response resp = Response.createResponse();
//        try {
//            request.setURI(new URI(bistableTrigger.getMobileAppAddress().toString() +":"+ Integer.toString(bistableTrigger.getMobileAppPort())));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        request.setPayload(TRIGGERED);
//        return request;
//    }
}

