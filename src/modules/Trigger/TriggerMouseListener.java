package modules.Trigger;

import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import main.Menu;
import modules.resources.DigitalInputStateResource;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static modules.listOfAvailableModules.BISTABLE_TRIGGER;

/**
 * Created by Inni on 2017-01-11.
 */
public class TriggerMouseListener implements MouseListener {

    private BistableTrigger trigger;
    private Menu menu;
    private DigitalInputStateResource on_off_resource;

    public TriggerMouseListener(final BistableTrigger trigger, final DigitalInputStateResource on_off_resource, final Menu menu) {
        this.trigger = trigger;
        this.on_off_resource = on_off_resource;
        this.menu = menu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        sendTriggerChange();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
        menu.repaint();
//        if (digitalInput.getState() == digitalInput.SWITCHED_ON) {
//            digitalInput.setState(digitalInput.SWITCHED_OFF);
//        } else if (digitalInput.getState() == digitalInput.SWITCHED_OFF) {
//            digitalInput.setState(digitalInput.SWITCHED_ON);
//        }
        on_off_resource.changed();
        on_off_resource.setButtonActionFlagFalse();
    }

//    private Request request() {
//        final Response resp = Response.createResponse();
//        try {
//            request.setURI(new URI(trigger.getMobileAppAddress().toString() +":"+ Integer.toString(trigger.getMobileAppPort())));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        request.setPayload(TRIGGERED);
//        return request;
//    }
}
