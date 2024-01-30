package es.nextdigital.demo.Controller;

import es.nextdigital.demo.Model.Cuenta;
import es.nextdigital.demo.Model.Movimiento;
import es.nextdigital.demo.Model.SolicitudRetirada;
import es.nextdigital.demo.Service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Cuenta>> obtenerCuentasPorUsuario(@PathVariable String usuarioId) {
        return new ResponseEntity<>(cuentaService.mostrarCuentasUsuario(usuarioId), HttpStatus.OK);
    }

    @GetMapping("/{cuentaId}/movimientos")
    public ResponseEntity<List<Movimiento>> obtenerMovimientosDeCuenta(@PathVariable String cuentaId) {

        Cuenta cuenta = cuentaService.obtenerCuentaPorId(cuentaId);

        if (cuenta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cuenta.getMovimientos(), HttpStatus.OK);
    }

    @PostMapping("/{tarjetaId}/sacar-dinero")
    public ResponseEntity<String> sacarDineroDeCuenta(@PathVariable String tarjetaId, @RequestBody SolicitudRetirada solicitud) {
        try {
            double cantidad = solicitud.getDinero();
            boolean resultado = cuentaService.sacarDineroDeCuenta(tarjetaId, cantidad);

            if (resultado) {
                return new ResponseEntity<>("Se ha realizado el retiro exitosamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se pudo realizar el retiro debido a saldo insuficiente o límite de crédito excedido.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
