package es.nextdigital.demo.Model;

public enum TipoTarjeta {
    DEBITO("Debito"),
    CREDITO("Credito");

    private final String descripcion;

    TipoTarjeta(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

