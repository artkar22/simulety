package modules;

import javax.swing.*;

/**
 * Created by Artur Karolak on 2017-02-20.
 */
public class SimuletsState {

    private final String StateId;
    private final ImageIcon picture;
    private final ImageIcon miniature;

    public SimuletsState(final String StateId, final ImageIcon picture, final ImageIcon miniature) {
        this.StateId = StateId;
        this.picture = picture;
        this.miniature = miniature;
    }

    public String getStateId() {
        return StateId;
    }

    public ImageIcon getPicture() {
        return picture;
    }

    public ImageIcon getMiniature() {
        return miniature;
    }
}
