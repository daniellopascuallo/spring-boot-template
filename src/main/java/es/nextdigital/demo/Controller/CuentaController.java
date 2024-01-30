package es.nextdigital.demo.Controller;

import es.nextdigital.demo.Model.Cuenta;
import es.nextdigital.demo.Model.Movimiento;
import es.nextdigital.demo.Service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
