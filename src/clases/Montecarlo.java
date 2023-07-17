package clases;

import java.util.Arrays;

public class Montecarlo {
    
    private double[] promedios;
    private double[][] metMontecarlo;

    public Montecarlo(double[] promedios){

        this.promedios = promedios;
        
        Arrays.sort(promedios);

    }

    public void metodoMontecarlo(){

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
