package gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import clases.Animacion;
import clases.Montecarlo;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class FrameSimulacion extends JFrame implements ActionListener {

    // Se declaran los componentes

    private JLabel lblFabrica;
    private JLabel lblCamion;
    private JLabel lblTienda;
    private JLabel lblAzucar;
    private JLabel lblCliente;

    private ImageIcon imgFabrica;
    private ImageIcon imgCamion;
    private ImageIcon imgTienda;
    private ImageIcon imgAzucar;
    private ImageIcon imgCliente;

    private JLabel lblDia;
    private JLabel lblInventario;
    private JLabel lblDemanda;
    private JLabel lblFaltante;

    private double[][] metMontecarlo;

    private JButton btnInicar;

    public FrameSimulacion() {
        initComponent();
    }

    private void initComponent() {

        String tahoma = "Tahoma";

        setLayout(null);

        setTitle("Simulación");

        // Se instancian los componentes

        imgFabrica = new ImageIcon("src\\imagesGUI\\fabrica_1.png");

        imgCamion = new ImageIcon("src\\imagesGUI\\camion_3.png");

        imgTienda = new ImageIcon("src\\imagesGUI\\tiendita_1.png");

        imgAzucar = new ImageIcon("src\\imagesGUI\\azucar_copia_1.png");

        imgCliente = new ImageIcon("src\\imagesGUI\\cliente_1.png");

        lblFabrica = new JLabel(imgFabrica);

        lblCamion = new JLabel(imgCamion);

        lblTienda = new JLabel(imgTienda);

        lblAzucar = new JLabel(imgAzucar);

        lblCliente = new JLabel(imgCliente);

        lblDia = new JLabel("Dia");

        lblFaltante = new JLabel("Faltante");

        lblInventario = new JLabel("Inventario");

        lblDemanda = new JLabel("Demanda");

        btnInicar = new JButton("Iniciar Simulación");

        // Se le asignan medidas y estilos a los componentes

        lblFabrica.setBounds(0, 350 - imgFabrica.getIconHeight() / 2, imgFabrica.getIconWidth(),
                imgFabrica.getIconHeight());

        lblCamion.setBounds(335, 350 + imgCamion.getIconHeight() / 2, imgCamion.getIconWidth(),
                imgCamion.getIconHeight());

        lblTienda.setBounds(600, 320, imgTienda.getIconHeight(), imgTienda.getIconWidth());

        lblAzucar.setBounds(730, 450, imgAzucar.getIconWidth(), imgAzucar.getIconHeight());

        lblCliente.setBounds(1200 - imgCliente.getIconWidth(), lblCamion.getY(), imgCliente.getIconWidth(),
                imgCliente.getIconHeight());

        lblDia.setBounds(540, 0, 200, 200);
        lblDia.setFont(new Font(tahoma, 3, 40));

        lblFaltante.setBounds(300, 200, 200, 100);
        lblFaltante.setFont(new Font(tahoma, 3, 24));

        lblInventario.setBounds(600, 200, 200, 100);
        lblInventario.setFont(new Font(tahoma, 3, 24));

        lblDemanda.setBounds(1060, 200, 200, 100);
        lblDemanda.setFont(new Font(tahoma, 3, 24));

        btnInicar.setBounds(490, 580, 300, 50);
        btnInicar.setFont(new Font(tahoma, 1, 24));

        // Se añaden los componentes al Frame para ser mostrados

        add(lblFabrica);

        add(lblCamion);

        add(lblTienda);

        add(lblAzucar);

        add(lblCliente);

        add(lblDia);

        add(lblFaltante);

        add(lblInventario);

        add(lblDemanda);

        add(btnInicar);

        // Se añade el botón al manejador de eventos para efectuar el evento

        btnInicar.addActionListener(this);

    }

    public JLabel getLblCliente() {
        return lblCliente;
    }

    public JLabel getLblCamion() {
        return lblCamion;
    }

    public JLabel getLblAzucar() {
        return lblAzucar;
    }

    public JLabel getLblTienda() {
        return lblTienda;
    }

    public JLabel getLblDia() {
        return lblDia;
    }

    public JLabel getLblInventario() {
        return lblInventario;
    }

    public JLabel getLblDemanda() {
        return lblDemanda;
    }

    public JLabel getLblFaltante() {
        return lblFaltante;
    }

    public void setMetMontecarlo(double[][] metMontecarlo) {
        this.metMontecarlo = metMontecarlo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnInicar) {

            calculos();

            Animacion animacion = new Animacion(this, metMontecarlo);

            animacion.start();

        }

    }

    private void calculos() {

        Montecarlo montecarlo = new Montecarlo();

        montecarlo.metodoMontecarlo();

        metMontecarlo = montecarlo.getMetodoMontecarlo();

    }

}
