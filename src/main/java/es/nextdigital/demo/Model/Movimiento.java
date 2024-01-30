package es.nextdigital.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "movimientos")
public class Movimiento {
    @Id
    private String id;
    private TipoMovimiento tipoMovimiento;
    private double cantidad;
    private Date fecha;
    private String descripcion;
    private String cuentaId;
}
