package es.nextdigital.demo.Model;

public enum TipoMovimiento {
    INGRESO("Ingreso"),
    RETIRO("Retiro"),
    COMISION("Comisión"),
    TRANSFERENCIA_ENTRANTE("Transferencia Entrante"),
    TRANSFERENCIA_SALIENTE("Transferencia Saliente");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

