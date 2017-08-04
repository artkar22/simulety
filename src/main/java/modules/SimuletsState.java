package modules;

import javafx.scene.media.Media;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class SimuletsState implements Serializable {

    private final String StateId;
    private final ImageIcon picture;
    private final BufferedImage miniature;
    private final BufferedImage highlightedMiniature;
    private final List<Media> statesSounds;

    public SimuletsState(final String StateId, final ImageIcon picture,
                         final BufferedImage miniature,
                         final BufferedImage highlightedMiniature,
                         final List<Media> statesSounds) {
        this.StateId = StateId;
        this.picture = picture;
        this.miniature = miniature;
        this.highlightedMiniature = highlightedMiniature;
        this.statesSounds = statesSounds;
    }

    public String getStateId() {
        return StateId;
    }

    public ImageIcon getPicture() {
        return picture;
    }

    public BufferedImage getMiniature() {
        return miniature;
    }
    public BufferedImage getHighlightedMiniature() {
        return highlightedMiniature;
    }
    public List<Media> getStatesSounds() {
        return statesSounds;
    }
}
