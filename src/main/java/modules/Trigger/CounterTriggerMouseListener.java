package modules.Trigger;


import app.Menu;
import modules.PossibleStatesListWrapper;
import modules.SimuletsState;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import static exceptions.ExceptionCodes.NO_PICTURE;
import static modules.listOfAvailableModules.COUNTER_SIMULET;
import static modules.resources.CounterSimuletCurrentStateResource.setTime;

/**
 * Created by Inni on 2017-01-11.
 */
public class CounterTriggerMouseListener implements MouseListener {

    private final int timeToSet;
    private PossibleStatesListWrapper possibleStates;
    private BistableTrigger digitalInput;
    private String documentName;
    private Menu menu;

    public CounterTriggerMouseListener(final int timeToSet, BistableTrigger digitalInput, PossibleStatesListWrapper possibleStates, String documentName, Menu menu) {
        this.timeToSet = timeToSet;
        this.digitalInput = digitalInput;
        this.possibleStates = possibleStates;
        this.documentName = documentName;
        this.menu = menu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setTime(timeToSet);
        menu.getInitialState().setPicture(loadPictures("INITIAL", Integer.valueOf(timeToSet).toString()));

        for(SimuletsState state : possibleStates.getAllStates(COUNTER_SIMULET)){
            state.setPicture(loadPictures(state.getStateId(), Integer.valueOf(timeToSet).toString()));
        }

        digitalInput.setCurrentState(menu.getInitialState());
        menu.repaint();
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

    private ImageIcon loadPictures(final String stateName, final String time) {
        final File directory = new File("pictures/" + documentName + "/" + stateName + "/main" + "/" + time);
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
