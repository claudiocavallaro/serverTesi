package com.unisa.tesi.persistence;

import com.unisa.tesi.model.Power;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerRepo extends MongoRepository<Power, Long> {
}
