package modules.Trigger;

import app.Menu;
import modules.resources.ObservableCurrentStateResource;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static modules.listOfAvailableModules.EVENT_SIMULET;

/**
 * Created by Inni on 2017-01-11.
 */
public class TriggerMouseListener implements MouseListener {

    private BistableTrigger trigger;
    private Menu menu;
    private ObservableCurrentStateResource on_off_resource;
    private boolean clickBlock = false;

    public TriggerMouseListener(final BistableTrigger trigger, final ObservableCurrentStateResource on_off_resource, final Menu menu) {
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
        final BistableTrigger digitalInput = on_off_resource.getDigitalInput();
        if (EVENT_SIMULET.equals(digitalInput.getClassName())) {
            if (digitalInput.getPossibleStates().getAllStates(digitalInput.getClassName()).size() == 1){
                if(!clickBlock)
                monostableTriggerHandler();

            } else {
                if (BistableTrigger.TRIGGER_SWITCHED_ON.equals(digitalInput.getCurrentState().getStateId())) {
                    digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_OFF));
                } else if (BistableTrigger.TRIGGER_SWITCHED_OFF.equals(digitalInput.getCurrentState().getStateId())) {
                    digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_ON));
                }
            }
        }
        menu.repaint();
        on_off_resource.changed();
        on_off_resource.setButtonActionFlagFalse();
    }

    private void monostableTriggerHandler() {
        clickBlock = true;
        on_off_resource.setButtonActionFlagTrue();
        final BistableTrigger digitalInput = on_off_resource.getDigitalInput();
        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_ON));
        menu.repaint();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        on_off_resource.changed();
                        on_off_resource.setButtonActionFlagFalse();
                        if(menu.getInitialState() != null){
                            digitalInput.setCurrentState(menu.getInitialState());
                        } else {
                            digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_OFF));
                        }
                        menu.repaint();
                        clickBlock = false;
                    }
                },
                2000
        );
    }


}
