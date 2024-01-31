package es.nextdigital.demo.Model;

import lombok.Data;

import java.util.List;

@Data
public class Account {
    private String id;
    private String accountNumber;
    private double balance;
    private List<Movement> movements;
    private String userId;
}

