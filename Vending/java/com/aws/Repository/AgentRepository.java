package com.aws.Repository;

import com.aws.model.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, String> {
}
