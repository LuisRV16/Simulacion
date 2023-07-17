package aplicaciones;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gui.FrameSimulacion;

public class AplicacionSimulacion {
    
    public static void main(String[] args) {
        
        JFrame frame = new FrameSimulacion();

        frame.setSize(1280, 700);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
