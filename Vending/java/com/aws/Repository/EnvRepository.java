package com.aws.Repository;

import com.aws.model.Environments;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface EnvRepository extends CrudRepository<Environments, String> {
    List<Environments> findByUserId(String id);
}
