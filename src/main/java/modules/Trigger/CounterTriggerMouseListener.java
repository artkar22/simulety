package modules.Trigger;

import app.Menu;
import ipsoconfig.ipsoInterfaces.implementation.IpsoDigitalInputImpl;
import modules.resources.DigitalInputStateResource;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static exceptions.ExceptionCodes.NO_PICTURE;

/**
 * Created by Inni on 2017-01-11.
 */
public class CounterTriggerMouseListener implements MouseListener {

    private BistableTrigger trigger;
    private Menu menu;
    private DigitalInputStateResource on_off_resource;
    private boolean clickBlock = false;

    public CounterTriggerMouseListener(final BistableTrigger trigger, final DigitalInputStateResource on_off_resource, final Menu menu) {
        this.trigger = trigger;
        this.on_off_resource = on_off_resource;
        this.menu = menu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!clickBlock)
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
        clickBlock = true;
        on_off_resource.setButtonActionFlagTrue();
        final IpsoDigitalInputImpl digitalInput = on_off_resource.getDigitalInput();
        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_ON));
        menu.repaint();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        digitalInput.setCurrentState(digitalInput.getPossibleStates().getStateById(BistableTrigger.TRIGGER_SWITCHED_OFF));
                        menu.repaint();
                        on_off_resource.changed();
                        on_off_resource.setButtonActionFlagFalse();
                        clickBlock = false;
                    }
                },
                21500
        );
    }

    private ImageIcon loadPictures(final String stateName, final String documentName) {
        final File directory = new File("pictures/" + documentName + "/" + stateName + "/main");
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files.length > 0) {
                return new ImageIcon(files[0].getAbsolutePath());
            } else {
                throw new RuntimeException(NO_PICTURE);
            }
        }
        return new ImageIcon();
    }

}
