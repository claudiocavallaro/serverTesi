package com.unisa.tesi.persistence;

import com.unisa.tesi.model.Preference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceRepo extends MongoRepository<Preference, Long> {
}
