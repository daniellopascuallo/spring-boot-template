package es.nextdigital.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tarjetas")
public class Card {
    @Id
    private String id;
    private String cardNumber;
    private CardType type;
    private String accountId;
    private double availableFunds;
    private double creditLimit;
    private String bank;
}

