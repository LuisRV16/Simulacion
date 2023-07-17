package clases;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import gui.FrameSimulacion;

public class Animacion extends Thread {

    private FrameSimulacion frame;

    private int perdidasT = 0;
    private float costoProm = 0;
    private float ventasProm = 0;

    private int[][] resultados;
    private double[] variables;
    private double[][] montecarlo;

    public Animacion(FrameSimulacion frame, double[][] montecarlo) {

        this.frame = frame;

        resultados = new int[60][6];

        variables = new double[60];

        resultados = new int[60][6];

        this.montecarlo = montecarlo;

        setVariables();

    }

    private void setVariables() {

        FlujoDeDatos archivDatos = new FlujoDeDatos(87569);

        archivDatos.setFile();
        archivDatos.iniciarLectorEscritor();

        for (int i = 0; i < variables.length; i++) {
            variables[i] = Double.parseDouble(archivDatos.leer());
        }

    }

    @Override
    public void run() {

        int milis = 1;

        int invIni = 700;

        int demanda = 0;
        int faltante = 0;
        int invRest = 0;
        int costoOrden;
        int perdidas = 0;
        int costoT = 0;
        int ventasT = 0;

        int camionX = frame.getLblCamion().getLocation().x;
        int camionY = frame.getLblCamion().getLocation().y;

        int clienteX = frame.getLblCliente().getLocation().x;
        int clienteY = frame.getLblCliente().getLocation().y;

        int azucarX = frame.getLblAzucar().getLocation().x;
        int azucarY = frame.getLblAzucar().getLocation().y;

        for (int i = 1; i <= 60; i++) {

            resultados[i - 1][0] = i;

            for (int j = 0; j < montecarlo.length; j++) {

                if (variables[i - 1] >= montecarlo[j][5] && variables[i - 1] < montecarlo[j][6]) {

                    demanda = (int) montecarlo[j][0];
                    break;

                }

            }

            resultados[i - 1][2] = demanda;

            frame.getLblDia().setText("Día: " + i);

            try {
                Thread.sleep(milis);
            } catch (InterruptedException ex) {
                Logger.getLogger(FrameSimulacion.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (i % 7 == 0) {

                invIni = invRest + faltante;

                costoOrden = 1000 + 6 * faltante + faltante;

                costoT += costoOrden;

                faltante = 0;

                resultados[i - 1][4] = costoOrden;

                while (frame.getLblCamion().getLocation().x < frame.getLblTienda().getLocation().x - 95) {

                    frame.getLblCamion().setLocation(frame.getLblCamion().getLocation().x + 1, camionY);

                    try {
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FrameSimulacion.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                frame.getLblCamion().setLocation(camionX, camionY);

                frame.getLblFaltante().setText("Faltante: " + faltante);

                frame.getLblInventario().setText("Inventario: " + invIni);

            }

            resultados[i - 1][1] = invIni;

            ventasT += demanda;

            frame.getLblDemanda().setText("Demanda: " + demanda);

            while (frame.getLblCliente().getLocation().x > frame.getLblAzucar().getLocation().x) {

                frame.getLblCliente().setLocation(frame.getLblCliente().getLocation().x - 1, clienteY);

                try {
                    Thread.sleep(milis);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrameSimulacion.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            invRest = invIni - demanda;

            resultados[i - 1][3] = invRest;

            if (invRest <= 0) {

                perdidas = invRest;

                resultados[i - 1][5] = perdidas;

                perdidasT += perdidas;

                invRest = 0;

            }

            faltante = 700 - invRest;

            frame.getLblInventario().setText("Inventario: " + invRest);

            frame.getLblFaltante().setText("Faltante: " + faltante);

            while (frame.getLblCliente().getLocation().x < clienteX) {

                frame.getLblCliente().setLocation(frame.getLblCliente().getLocation().x + 1, clienteY);

                frame.getLblAzucar().setLocation(frame.getLblAzucar().getLocation().x + 1, azucarY);

                try {
                    Thread.sleep(milis);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrameSimulacion.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            frame.getLblAzucar().setLocation(azucarX, azucarY);

            invIni = invRest;

            costoProm = (float) costoT / i;
            ventasProm = (float) ventasT / i;
        }

        JOptionPane.showMessageDialog(null, "Ventas promedio/Día: " + ventasProm + "\nCosto promedio/Día: " + costoProm
                + "\nPerdidas Totales: " + perdidasT);

    }

    public int getPerdidasT() {
        return perdidasT;
    }

    public float getCostoProm() {
        return costoProm;
    }

    public float getVentasProm() {
        return ventasProm;
    }

    public int[][] getResultados() {
        return resultados;
    }

}
