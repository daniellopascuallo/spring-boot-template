package es.nextdigital.demo.Repository;

import es.nextdigital.demo.Model.Tarjeta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TarjetaRepository extends MongoRepository<Tarjeta, String> {
    Optional<Tarjeta> findByCuentaId(String cuentaId);
}
