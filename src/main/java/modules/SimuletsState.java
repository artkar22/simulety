package modules;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class SimuletsState implements Serializable {

    private final String StateId;
    private final ImageIcon picture;
    private final BufferedImage miniature;
    private final BufferedImage highlightedMiniature;

    public SimuletsState(final String StateId, final ImageIcon picture,
                         final BufferedImage miniature, final BufferedImage highlightedMiniature) {
        this.StateId = StateId;
        this.picture = picture;
        this.miniature = miniature;
        this.highlightedMiniature = highlightedMiniature;
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
}
