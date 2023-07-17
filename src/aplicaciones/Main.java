package aplicaciones;

import clases.GenCuadMedios;
import clases.GenVariables;
import clases.Montecarlo;

public class Main {

    public static void main(String[] args) {

        int desde = 10000;
        int hasta = 99999;
        int cantNum = 60;

        GenCuadMedios genNumPseudo = new GenCuadMedios(desde, hasta, cantNum);
        
        //genNumPseudo.generarNumeros();
        genNumPseudo.evaluarNumeros();

        GenVariables genVariables = new GenVariables(cantNum, genNumPseudo.getSemillasAprobadas());
        
        //genVariables.generarVariables();
        genVariables.calcularPromedios();

        Montecarlo metodo = new Montecarlo(genVariables.getPromedios());

        metodo.metodoMontecarlo();

        double[][] montecarlo = metodo.getMetodoMontecarlo();



        System.out.println("Método de Montecarlo: ");

        for (int i = 0; i < montecarlo.length; i++) {

            System.out.println("\t");

            for (int j = 0; j < montecarlo[0].length; j++)
                System.out.print("|" + montecarlo[i][j] + "|\t");

        }

    }

}