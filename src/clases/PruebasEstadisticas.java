package clases;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class PruebasEstadisticas {

    private boolean validacion;
    private static final float Z = 1.96F;
    private static final float SIGNIFICANCIA = 0.05F;
    private static final float CONFIANZA = 1 - SIGNIFICANCIA;
    private int[] frecuenciaObs;
    private double media;
    private double varianza;
    private double limiteSup;
    private double limiteInf;
    private double sum;
    private double estadistico;
    private double teorico;
    private double[] numerosPseudo;
    private String[] numPseudo;
    private ChiSquaredDistribution chi;
    private FlujoDeDatos archivDatos;

    public PruebasEstadisticas(int cantNum, FlujoDeDatos archivDatos){
        
        setArchivDatos(archivDatos);
        numPseudo = new String[cantNum];
        numerosPseudo = new double[cantNum];
        almacenar();

    }

    private void setArchivDatos(FlujoDeDatos archivDatos) {this.archivDatos = archivDatos;}

    private void almacenar(){

        archivDatos.iniciarLectorEscritor();

        for(int i = 0; i < numPseudo.length; i++){

            numPseudo[i] = archivDatos.leer();
            numerosPseudo[i] = Double.parseDouble(numPseudo[i]);

        }

        archivDatos.concluirLecturaYEscritura();

    }

    public void media(){

        double raiz;

        for (double numero : numerosPseudo) {
            sumatoria(numero);
        }

        media = divisionSum(sum, numerosPseudo.length);

        raiz = Math.sqrt((double) 12 * numerosPseudo.length);

        limiteSup = 0.5 + divLimites(Z, raiz);
        limiteInf = 0.5 - divLimites(Z, raiz);

        validacion = dentroRango(media, limiteSup, limiteInf);

    }

    public void varianza(){

        int valor = numerosPseudo.length - 1;
        sum = 0;

        chi = new ChiSquaredDistribution(valor);

        for (double numero : numerosPseudo) {
            sumatoria(Math.pow(numero - media, 2));
        }

        varianza = divisionSum(sum, valor);

        limiteSup = divLimites(chi.inverseCumulativeProbability(1 - (SIGNIFICANCIA / 2)),  (double) 12 * valor);
        limiteInf = divLimites(chi.inverseCumulativeProbability(SIGNIFICANCIA / 2),  (double) 12 * valor);

        validacion = dentroRango(varianza, limiteSup, limiteInf);

    }

    public void uniformidad(){

        double m;

        m = Math.sqrt(numerosPseudo.length);
        int clases = (int) Math.round(m);
        double frecuenciaEsp = (double) numerosPseudo.length/clases;
        chi = new ChiSquaredDistribution((double)clases-1);
        teorico = chi.inverseCumulativeProbability(CONFIANZA);
        frecuenciaObs = new int[clases];

        for (double numero : numerosPseudo) {

            for (int j = 0; j < frecuenciaObs.length; j++) {

                if (numero >= (float)j/clases && numero < ((float)(j+1)/clases)) {

                    frecuenciaObs[j]++;
                    break;

                }

            }

        }

        for (int fO : frecuenciaObs) {
            estadistico(frecuenciaEsp, fO);
        }

        validacion = cumpleCriterio(estadistico, teorico);

    }

    public void poker(){

        int cantConj = numerosPseudo.length;

        float[] frecEsperada = {0.3024F * cantConj, 0.5024F * cantConj, 0.108F * cantConj, 0.009F * cantConj, 0.072F * cantConj, 0.0045F * cantConj, 0.0001F * cantConj};

        frecuenciaObs = new int[frecEsperada.length];

        chi = new ChiSquaredDistribution(6);
        teorico = chi.inverseCumulativeProbability(CONFIANZA);

        for (String numero : numPseudo ) {

            int[] fObsAux = new int[2];

            HashMap<Integer,Integer> cantNum = new HashMap<>();

            String decimales = numero.substring(2, 7);

            for (int i = 0; i < decimales.length(); i++) {

                int digito = Integer.parseInt(String.valueOf(decimales.charAt(i)));

                if (cantNum.containsKey(digito)) {

                    int frecActual = cantNum.get(digito);
                    cantNum.put(digito, frecActual+1);
                    
                }else{
                    cantNum.put(digito, 1);
                }

            }

            for (Map.Entry<Integer,Integer> entry : cantNum.entrySet()) {

                int valor = entry.getValue();

                switch (valor) {
                    case 2 -> fObsAux[0]++;
                    case 3 -> fObsAux[1]++;
                    case 4 -> frecuenciaObs[5]++;
                    case 5 -> frecuenciaObs[6]++;
                }

            }

            if (fObsAux[0]>0 || fObsAux[1]>0) {

                if (fObsAux[0]==1 && fObsAux[1]==1)
                    frecuenciaObs[3]++;
                else if (fObsAux[0]==2)
                    frecuenciaObs[2]++;
                else if (fObsAux[0]==1)
                    frecuenciaObs[1]++;
                else if (fObsAux[1]==1)
                    frecuenciaObs[4]++;
                
            } else {
                frecuenciaObs[0]++;
            }

        }

        estadistico = 0;

        for (int i = 0; i < frecEsperada.length; i++) {
            estadistico(frecEsperada[i], frecuenciaObs[i]);
        }

        validacion = cumpleCriterio(estadistico, teorico);

    }

    public void aleatoriedad(){

        int corridasObs = 1;

        double valorEsperado;
        double desviacionEst;
        
        boolean band;
        boolean band2;

        valorEsperado = ((double) (2 * numerosPseudo.length - 1)) / 3;

        varianza = ((double) (16 * numerosPseudo.length - 29)) / 90;

        desviacionEst = Math.sqrt(varianza);

         band = numerosPseudo[0] < numerosPseudo[1];
         band2 = band;

         for (int i = 1; i < numerosPseudo.length; i++) {
            
            band = numerosPseudo[i] > numerosPseudo[i - 1];

            if (band2 != band) {

                corridasObs++;
                band2 = band;

            }

         }

         estadistico = Math.abs((corridasObs - valorEsperado) / desviacionEst);

         validacion = cumpleCriterio(estadistico, Z);

    }

    private boolean cumpleCriterio(double estadistico, double valComp){
        return estadistico < valComp;
        /*valComp es el valor a comparar, ya que depende del contexto, puede ser una chi² teorica o el valor de Z */
    }

    private void estadistico(double v1, double v2){estadistico += Math.pow(v1 - v2, 2) / v1;}

    private void sumatoria(double valor){sum +=  valor;}

    private double divisionSum(double sum, int valor){return sum / valor;}

    private double divLimites(double v1, double v2){return v1 / v2;}

    private boolean dentroRango(double valor, double limiteSup, double limiteInf){return valor >= limiteInf && valor <= limiteSup;}

    public boolean isValido(){return validacion;}
    
    public double getMedia() {return media;}

    public double getVarianza() {return varianza;}

    public double getLimiteSup(){return limiteSup;}

    public double getLimiteInf(){return limiteInf;}

    public double getEstadistico(){return estadistico;}

    public double getTeorico(){return teorico;}

    public float getZ(){return Z;}

    public double[] getNumerosPseudo(){return numerosPseudo;}

}
