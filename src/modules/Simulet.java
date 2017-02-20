package modules;

import javax.swing.*;
import java.awt.*;


public abstract class Simulet extends JPanel {

    protected final PossibleStatesListWrapper possibleStates;
    final String className;
    protected ImageIcon currentImage;
    private SimuletsState currentState;
    private String nameOfSimulet;


    public Simulet(final String nameOfSimulet, final String className, final SimuletsState currentState, final PossibleStatesListWrapper possibleStates) {
        this.nameOfSimulet = nameOfSimulet;
        this.className = className;
        this.currentState = currentState;
        this.possibleStates = possibleStates;
        setCurrentImage();
    }

    public String getNameOfSimulet() {
        return nameOfSimulet;
    }

    protected void setCurrentImage() {
        if (currentState.getPicture() != null) {
            currentImage = currentState.getPicture();
            Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
            setPreferredSize(dimension);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (currentImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(currentImage.getImage(), 0, 0, this);
        }
    }

    public String getClassName() {
        return className;
    }

    public SimuletsState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(SimuletsState currentState) {
        this.currentState = currentState;
        setCurrentImage();
    }

    public PossibleStatesListWrapper getPossibleStates() {
        return possibleStates;
    }
}
