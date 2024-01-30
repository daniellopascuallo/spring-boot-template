package es.nextdigital.demo.Model;

import lombok.Data;

import java.util.List;

@Data
public class Cuenta {
    private String id;
    private String numeroCuenta;
    private double saldo;
    private List<Movimiento> movimientos;
    private String usuarioId;
}

