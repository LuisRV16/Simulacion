package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FlujoDeDatos {

    private StringBuilder archivo;
    private String numero = "";
    private String semilla = "Semilla_";
    private File ruta;
    private BufferedReader lector;
    private BufferedWriter escritor;

    public FlujoDeDatos(long semilla) {

        archivo = new StringBuilder();
        setArchivo(semilla);
        setFile();
        setArchivoVariables(semilla);

    }

    public FlujoDeDatos(long x1, long x2) {

        archivo = new StringBuilder();
        setArchivo(x1, x2);
        setFile();
        setArchivoVariables(x1, x2);

    }

    private void setArchivo(long semilla) {archivo.append(this.semilla).append(semilla).append(".txt");}

    private void setArchivo(long x1, long x2) {archivo.append(this.semilla).append(x1).append("_").append(x2).append(".txt");}

    private void setArchivoVariables(long semilla){
        archivo = new StringBuilder();
        archivo.append("Variables_").append(this.semilla).append(semilla).append(".txt");
    }

    private void setArchivoVariables(long x1, long x2) {
        archivo = new StringBuilder();
        archivo.append("Variables_").append(this.semilla).append(x1).append("_").append(x2).append(".txt");
    }

    public void setFile() {ruta = new File("C:" + File.separator + "Numeros_pseudoaleatorios" + File.separator + archivo);}

    public void iniciarLectorEscritor() {

        try {

            lector = new BufferedReader(new FileReader(ruta));
            escritor = new BufferedWriter(new FileWriter(ruta, true));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void crearArchivo() {

        eliminarArchivo();

        try {
            ruta.createNewFile();
            /*
             * No es necesario imprimir un mensaje con el valor booleano retornado
             */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void eliminarArchivo() {
        ruta.delete();
        /*
         * No es necesario imprimir un mensaje con el valor booleano retornado
         */
    }

    public void escribir(String numero) {

        String line;

        try {

            line = lector.readLine();

            if (line != null)
                escritor.newLine();

            escritor.write(numero);

            escritor.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String leer() {

        String line;

        try {

            line = lector.readLine();

            if (line != null) {
                numero = line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return numero;

    }

    public void concluirLecturaYEscritura() {

        try {
            lector.close();
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
