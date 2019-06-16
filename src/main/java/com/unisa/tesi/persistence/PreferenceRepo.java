package com.unisa.tesi.persistence;

import com.unisa.tesi.model.Preference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepo extends MongoRepository<Preference, Long> {

    @Query(value = "{ 'user._id' : ?0 }")
    List<Preference> findByUserId(String uid);


}
