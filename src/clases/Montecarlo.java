package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Montecarlo {

    private double[][] metMontecarlo;

    public void metodoMontecarlo(){

        ArrayList<Double> proms = new ArrayList<>();

        File archivo = new File("promedios.txt");

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {

            String line = lector.readLine();

            while (line!=null) {

                proms.add(Double.parseDouble(line));

                line = lector.readLine();
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Object[] aux = proms.toArray();

        double[] promedios = new double[aux.length];

        for (int i = 0; i < aux.length; i++)
            promedios[i] = (double)aux[i];

        Arrays.sort(promedios);

        double menor = promedios[0];

        double mayor = promedios[promedios.length - 1];

        int numIntv = (int) mayor - (int) menor + 1;

        metMontecarlo = new double[numIntv][7];

        for (int i = 0; i < metMontecarlo.length; i++)
            metMontecarlo[i][0] = ((int) menor + i);

        for (double promedio : promedios) {

            for (int i = 0; i < metMontecarlo.length; i++) {

                if ((int) promedio == (int) metMontecarlo[i][0]) {

                    metMontecarlo[i][1]++;
                    break;

                }

            }

        }

        double suma = 0;

        metMontecarlo[0][3] = 0;

        for (int i = 0, j = 1; i < metMontecarlo.length; i++, j++) {

            metMontecarlo[i][1] /= promedios.length;
            suma += metMontecarlo[i][1];

            metMontecarlo[i][2] = suma;

            if (j < metMontecarlo.length)
                metMontecarlo[j][3] = metMontecarlo[i][2];

            metMontecarlo[i][4] = metMontecarlo[i][2];

            metMontecarlo[i][5] = metMontecarlo[i][3] * 1000;

            metMontecarlo[i][6] = metMontecarlo[i][4] * 1000;

        }

    }

    public double[][] getMetodoMontecarlo(){return metMontecarlo;}

}
