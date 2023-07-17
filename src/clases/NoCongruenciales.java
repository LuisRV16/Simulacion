package clases;

public final class NoCongruenciales {

    private long x1;
    private long x2;
    private long producto;
    private int nDig;
    private int lDig;
    private String stringProd;
    private String centro;
    private String ri;

    public NoCongruenciales(long semilla){
        setX1(semilla);
        setX2(semilla);
    }

    public NoCongruenciales(long x1, long x2) {
        setX1(x1);
        setX2(x2);
    }

    public void setX1(long x1) {this.x1 = x1;}

    public long getX1() {return x1;}

    public void setX2(long x2) {this.x2 = x2;}

    public long getX2() {return x2;}

    public void examinaSemillas() throws DifLong, NoNegative, InsfLong {

        String semilla1 = String.valueOf(this.x1);
        String semilla2 = String.valueOf(this.x2);

        if (semilla1.length() != semilla2.length()) {
            throw new DifLong();
        } else if (this.x1 < 0 || this.x2 < 0) {
            throw new NoNegative();
        } else if (semilla1.length() < 4 || semilla2.length() < 4) {
            throw new InsfLong();
        }

    }

    private String getSemilla() {return String.valueOf(x1);}

    private void cantDig() {nDig = getSemilla().length();}

    public void limDigits() {

        cantDig();
        lDig = nDig * 2;

    }

    public void producto() {producto = x1 * x2;}

    public long getProducto() {return producto;}

    private void finalProdLenght() {
        
        stringProd = String.valueOf(producto);
        stringProd = String.format("%0" + lDig + "d", producto);

    }

    public String getStringProd(){return stringProd;}

    public void extraerDig() {

        int elim;
        int desde;
        int hasta;

        finalProdLenght();

        elim = nDig;
        desde = elim/2;
        hasta = desde + elim;

        centro = stringProd.substring(desde, hasta);

    }

    public String getCentro() {return centro;}

    public long getDgtsCentro(){return Long.parseLong(centro);}

    public void ri() {ri = "0." + centro;}

    public String getRi() {return ri;}

    public final class DifLong extends Exception {
        public DifLong() {
            /*
             * Método vació debido a que sólo está creado para identificar el tipo de excepción
             * prevista dentro del contexto del problema
             */
        }
    }

    public final class NoNegative extends Exception {
        public NoNegative() {
            /*
             * Método vació debido a que sólo está creado para identificar el tipo de excepción
             * prevista dentro del contexto del problema
             */
        }
    }

    public final class InsfLong extends Exception {
        public InsfLong() {
            /*
             * Método vació debido a que sólo está creado para identificar el tipo de excepción
             * prevista dentro del contexto del problema
             */
        }
    }

}
