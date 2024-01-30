package es.nextdigital.demo.Repository;

import es.nextdigital.demo.Model.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CuentaRepository extends MongoRepository<Cuenta, String> {
    List<Cuenta> findByUsuarioId(String usuarioId);
}
