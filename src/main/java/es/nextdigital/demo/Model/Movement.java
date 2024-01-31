package es.nextdigital.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "movimientos")
public class Movement {
    @Id
    private String id;
    private MovementType movementType;
    private double amount;
    private Date date;
    private String description;
    private String accountId;
}
