package es.nextdigital.demo.Repository;

import es.nextdigital.demo.Model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByAccountId(String accountId);
}
