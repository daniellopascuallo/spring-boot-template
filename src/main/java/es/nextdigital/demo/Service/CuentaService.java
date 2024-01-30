package es.nextdigital.demo.Service;

import es.nextdigital.demo.Model.Cuenta;
import es.nextdigital.demo.Repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public List<Cuenta> mostrarCuentasUsuario(String usuarioId) {

        return cuentaRepository.findByUsuarioId(usuarioId);
    }

    public Cuenta obtenerCuentaPorId(String cuentaId) {

        return cuentaRepository.findById(cuentaId).orElse(null);
    }
}
