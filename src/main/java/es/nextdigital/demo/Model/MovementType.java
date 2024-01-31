package es.nextdigital.demo.Model;

public enum MovementType {
    INCOME("Income"),
    WITHDRAW("Withdraw"),
    FEES("Fees"),
    INCOME_TRANSACTION("Income Transaction"),
    WITHDRWA_TRANSACTION("Withdraw Transaction");

    private final String description;

    MovementType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

