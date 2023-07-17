package clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GenVariables {

    private int cantNum;
    private int cantProm;
    private ArrayList<Integer> semillasAprob;
    private double[] variables;
    private double[] promedios;

    public GenVariables(int cantNum, ArrayList<Integer> semillasAprob){

        this.cantNum = cantNum;
        cantProm = semillasAprob.size();
        this.semillasAprob = semillasAprob;
        variables = new double[cantNum];
        promedios = new double[cantProm];

    }

    public void generarVariables(){

        for (Integer semilla : semillasAprob) {
            
            FlujoDeDatos archivDatos = new FlujoDeDatos(semilla);

            archivDatos.iniciarLectorEscritor();

            double numero;

            for (int i = 0; i < cantNum; i++){

                numero = Double.parseDouble(archivDatos.leer());

                variables[i] = -100 * Math.log(1-numero);

            }

            archivDatos.concluirLecturaYEscritura();        

            archivDatos.setFile();
            archivDatos.crearArchivo();
            archivDatos.iniciarLectorEscritor();

            for (double variable : variables)                 
                archivDatos.escribir(String.valueOf(variable));

            archivDatos.concluirLecturaYEscritura();
        }

    }

    public void calGenPromedios(){

        File archivo = new File("promedios.txt");

        archivo.delete();

        try {
            archivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < semillasAprob.size(); i++) {
            
            FlujoDeDatos archivDatos = new FlujoDeDatos(semillasAprob.get(i));

            archivDatos.setFile();
            archivDatos.iniciarLectorEscritor();

            double suma = 0;

            for (int j = 0; j < cantNum; j++) {

                variables[j] = Double.parseDouble(archivDatos.leer());

                suma += variables[j];

            }

            archivDatos.concluirLecturaYEscritura();

            promedios[i] = suma/cantNum;

            try {

                BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo,true));

                escritor.write(String.valueOf(promedios[i]));

                if (i < cantProm-1) 
                    escritor.newLine();

                escritor.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public int getCantProm(){
        return cantProm;
    }
    
}
