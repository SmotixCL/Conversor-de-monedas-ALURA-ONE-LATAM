public class MonedaResultado {
    String paisOrigen;
    String paisACambio;
    double resultadoConversion;

    public MonedaResultado(MonedaOmdb miCambioOmdb) {
        this.paisOrigen = miCambioOmdb.base_code();
        this.paisACambio = miCambioOmdb.target_code();
        this.resultadoConversion = miCambioOmdb.conversion_result();
    }

    @Override
    public String toString() {
        return
                "  " + paisOrigen +
                        "  equivalen a: " + resultadoConversion +
                        " " + paisACambio;


    }
}

