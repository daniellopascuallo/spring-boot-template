package es.nextdigital.demo.Service;

import es.nextdigital.demo.Controller.Exceptions.CuentaNoEncontradaException;
import es.nextdigital.demo.Controller.Exceptions.TarjetaNoAsociadaException;
import es.nextdigital.demo.Model.Cuenta;
import es.nextdigital.demo.Model.SolicitudRetirada;
import es.nextdigital.demo.Model.Tarjeta;
import es.nextdigital.demo.Model.TipoTarjeta;
import es.nextdigital.demo.Repository.CuentaRepository;
import es.nextdigital.demo.Repository.TarjetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final TarjetaRepository tarjetaRepository;

    public List<Cuenta> mostrarCuentasUsuario(String usuarioId) {

        return cuentaRepository.findByUsuarioId(usuarioId);
    }

    public Cuenta obtenerCuentaPorId(String cuentaId) {
        return cuentaRepository.findById(cuentaId).orElse(null);
    }

    public boolean sacarDineroDeCuenta(final String tarjetaId, final double solicitudRetirada) {
        Tarjeta tarjeta = tarjetaRepository.findById(tarjetaId)
                .orElseThrow(() -> new TarjetaNoAsociadaException("La cuenta no tiene una tarjeta asociada."));

        Cuenta cuenta = cuentaRepository.
                findById(tarjeta.getCuentaId()).
                orElseThrow(() -> new CuentaNoEncontradaException("La cuenta con ID " + tarjeta.getCuentaId() + " no existe."));

        boolean exitoso = tarjeta.getTipo() == TipoTarjeta.DEBITO ?
                realizarRetiroDebito(cuenta, solicitudRetirada) :
                realizarRetiroCredito(tarjeta, solicitudRetirada);

        if (exitoso) {
            cuentaRepository.save(cuenta);
            tarjetaRepository.save(tarjeta);
        }

        return exitoso;
    }

    private boolean realizarRetiroDebito(Cuenta cuenta, double dinero) {
        return Optional.ofNullable(cuenta)
                .filter(c -> c.getSaldo() >= dinero)
                .map(c -> {
                    c.setSaldo(c.getSaldo() - dinero);
                    return true;
                })
                .orElse(false);
    }

    private boolean realizarRetiroCredito(Tarjeta tarjeta, double dinero) {
        return Optional.ofNullable(tarjeta)
                .filter(t -> dinero <= t.getLimiteCredito())
                .map(t -> {
                    t.setLimiteCredito(t.getLimiteCredito() - dinero);
                    return true;
                })
                .orElse(false);
    }
}
