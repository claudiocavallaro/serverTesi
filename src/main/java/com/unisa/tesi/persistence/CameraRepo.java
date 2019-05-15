package com.unisa.tesi.persistence;

import com.unisa.tesi.model.Camera;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepo extends MongoRepository<Camera, Long> {
}
