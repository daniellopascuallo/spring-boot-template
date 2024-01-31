package es.nextdigital.demo.Model;

public enum CardType {

    DEBIT("Debit"),
    CREDIT("Credit");

    private final String description;

    CardType(String descripcion) {
        this.description = descripcion;
    }

    public String getDescripcion() {
        return description;
    }
}

