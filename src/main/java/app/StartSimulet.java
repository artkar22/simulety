package app;

import javax.swing.*;

public class StartSimulet {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu(args[0]));
    }

}
