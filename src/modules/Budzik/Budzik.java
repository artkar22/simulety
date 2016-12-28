package modules.Budzik;

import java.awt.Dimension;

import modules.Simulet;

public class Budzik extends Simulet {

    public static final int BUDZIK_SWITCHED_ON = 1;
    public static final int BUDZIK_SWITCHED_OFF = 0;
    private static final int id = 0;//TODO not-defined yet
    private int currentStatus;

    public Budzik(String nameOfSimulet) {
        super(nameOfSimulet);
        setStatus(BUDZIK_SWITCHED_OFF);
    }

    public void switchOn() {
        setStatus(BUDZIK_SWITCHED_ON);
        currentImage = images.get(BUDZIK_SWITCHED_ON);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public void switchOff() {
        setStatus(BUDZIK_SWITCHED_OFF);
        currentImage = images.get(BUDZIK_SWITCHED_OFF);
        Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
        setPreferredSize(dimension);
    }

    public int getId() {
        return id;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    private void setStatus(int newStatus) {
        currentStatus = newStatus;
    }
}
