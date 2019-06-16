package com.unisa.tesi.persistence;

import com.unisa.tesi.model.PhonePreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonePrefRepo extends MongoRepository<PhonePreference, Long> {
}
