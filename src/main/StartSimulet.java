package main;

import javax.swing.SwingUtilities;

public class StartSimulet {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu(args[0], args[1]);
            }
        });

    }

}
