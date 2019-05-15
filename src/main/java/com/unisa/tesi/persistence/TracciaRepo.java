package com.unisa.tesi.persistence;

import com.unisa.tesi.model.Traccia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracciaRepo extends MongoRepository<Traccia, String> {
}
