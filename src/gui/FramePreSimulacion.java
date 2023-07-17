package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import clases.GenCuadMedios;
import clases.GenVariables;
import clases.Montecarlo;

public class FramePreSimulacion extends JFrame implements ActionListener {

    private JTextArea txtMsj;

    private JButton btnInicar;

    private double[][] metMontecarlo;

    public FramePreSimulacion() {
        initComponent();
    }

    private void initComponent() {

        setLayout(null);

        setTitle("Preparando Simulación");

        txtMsj = new JTextArea("Preparar Simulación.");
        
        btnInicar = new JButton("Preparar Simulación");

        txtMsj.setBounds(0, 120, 700, 150);
        txtMsj.setFont(new Font("Tahoma", 3, 65));
        txtMsj.setEditable(false);

        btnInicar.setBounds(200, 350, 300, 50);

        btnInicar.setFont(new Font("Tahoma", 3, 20));

        add(txtMsj);

        add(btnInicar);

        btnInicar.addActionListener(this);

    }

    private void calculos() {

        int cantNum = 60;

        GenCuadMedios numPseudo = new GenCuadMedios(10000, 99999, cantNum);

        numPseudo.evaluarNumeros();

        GenVariables variables = new GenVariables(cantNum, numPseudo.getSemillasAprobadas());

        variables.calcularPromedios();

        Montecarlo montecarlo = new Montecarlo(variables.getPromedios());

        montecarlo.metodoMontecarlo();

        metMontecarlo = montecarlo.getMetodoMontecarlo();

    }

    public double[][] getMetodoMontecarlo() {
        return metMontecarlo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnInicar) {

            txtMsj.setText("Preparando Simulación. Por favor, espere...");

            calculos();

            this.setVisible(false);

            FrameSimulacion frame = new FrameSimulacion();

            frame.setMetMontecarlo(metMontecarlo);

            frame.setSize(1280, 700);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        }

    }

}
