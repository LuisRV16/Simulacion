package clases;

import java.util.ArrayList;

import clases.NoCongruenciales.DifLong;
import clases.NoCongruenciales.InsfLong;
import clases.NoCongruenciales.NoNegative;

public class GenCuadMedios {

    private int desde;
    private int hasta;
    private int cantNum;

    private ArrayList<Integer> semillasAprob;

    public GenCuadMedios(int desde, int hasta, int cantNum){

        this.desde = desde;

        this.hasta = hasta;

        this.cantNum = cantNum;

        semillasAprob = new ArrayList<>();

    }

    public void generarNumeros(){

        for (int i = desde; i <= hasta; i++) {

            long semilla = 0;
            
            NoCongruenciales generador = new NoCongruenciales(i);

            FlujoDeDatos archivDatos = new FlujoDeDatos(i);
            
            StringBuilder numeros = new StringBuilder();

            archivDatos.crearArchivo();
            archivDatos.iniciarLectorEscritor();

            try {
                generador.examinaSemillas();
            } catch (DifLong e) {
                System.out.println("ERROR\n\tLas semillas tienen una longitud diferente");
                return;
            } catch (NoNegative e) {
                System.out.println("ERROR\n\tLas semillas no pueden tener un valor negativo");
                return;
            } catch (InsfLong e) {
                System.out.println("ERROR\n\tLas semillas deben tener una cantidad de dígitos mayor a 3");
                return;
            }

            generador.limDigits();

            for (int j = 0; j < cantNum; j++) {

                generador.producto();
                generador.extraerDig();
                generador.ri();

                semilla = generador.getDgtsCentro();

                numeros.append(generador.getRi());

                if (j != cantNum-1) {
                    numeros.append("\n");
                }

                generador.setX1(semilla);
                generador.setX2(semilla);

            }

            archivDatos.escribir(numeros.toString());

            archivDatos.concluirLecturaYEscritura();

        }

    }

    public void evaluarNumeros(){

        for (int i = desde; i <= hasta; i++) {

            FlujoDeDatos archivDatos = new FlujoDeDatos(i);

            PruebasEstadisticas pruebas = new PruebasEstadisticas(cantNum, archivDatos);

            boolean[] pasaPruebas = new boolean[5];

            pruebas.media();
            pasaPruebas[0] = pruebas.isValido();

            pruebas.varianza();
            pasaPruebas[1] = pruebas.isValido();

            pruebas.uniformidad();
            pasaPruebas[2] = pruebas.isValido();

            pruebas.poker();
            pasaPruebas[3] = pruebas.isValido();

            pruebas.aleatoriedad();
            pasaPruebas[4] = pruebas.isValido();

            if (pasaPruebas[0] || pasaPruebas[1] && pasaPruebas[2] && pasaPruebas[3] && pasaPruebas[4])
                semillasAprob.add(i);

        }

    }

    public ArrayList<Integer> getSemillasAprobadas(){return semillasAprob;}
    
}

