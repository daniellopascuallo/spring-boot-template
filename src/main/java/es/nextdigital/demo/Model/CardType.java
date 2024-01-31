package es.nextdigital.demo.Model;

public enum CardType {

    DEBIT("Debit"),
    CREDIT("Credit");

    private final String description;

    CardType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

