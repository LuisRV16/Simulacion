package aplicaciones;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gui.FramePreSimulacion;

public class AplicacionSimulacion {
    
    public static void main(String[] args) {
        
        JFrame frame = new FramePreSimulacion();

        frame.setSize(700, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
