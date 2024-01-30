package es.nextdigital.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tarjetas")
public class Tarjeta {
    @Id
    private String id;
    private String numeroTarjeta;
    private TipoTarjeta tipo;
    private String cuentaId;
    private double saldoDisponible;
    private double limiteCredito;
    private String bancoAsociado;
}

