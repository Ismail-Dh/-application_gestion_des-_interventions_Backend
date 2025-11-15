package ma.ocp.log_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ma.ocp.log_service.entities.LogApp;

public interface LogAppRepository extends MongoRepository<LogApp, String>{

}
