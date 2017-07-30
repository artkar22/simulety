package modules;

import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;


public class Simulet extends JPanel {

    protected final PossibleStatesListWrapper possibleStates;
    final String className;

    public void setCurrentImage(ImageIcon currentImage) {
        this.currentImage = currentImage;
    }

    protected ImageIcon currentImage;
    private SimuletsState currentState;
    private String nameOfSimulet;
    private MediaPlayer mediaPlayer;


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

    public void setCurrentImage() {
        if (currentState !=null && currentState.getPicture() != null) {
            currentImage = new ImageIcon(currentState.getPicture().getImage());
            Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
            setPreferredSize(dimension);
            currentImage.getImage().flush();

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
        playSound();
    }

    private void playSound() {
        if(this.currentState != null && this.currentState.getStatesSounds().size() > 0){
            mediaPlayer = new MediaPlayer(this.currentState.getStatesSounds().get(0));
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public PossibleStatesListWrapper getPossibleStates() {
        return possibleStates;
    }
}
