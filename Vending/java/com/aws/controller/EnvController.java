package com.aws.controller;

import com.aws.Repository.EnvRepository;
import com.aws.model.Environments;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class EnvController {

    @Autowired
    EnvRepository envRepository;

    @GetMapping("/environments")
    public Iterable<Environments> getEnvironments() {
        return envRepository.findAll();
    }

    @GetMapping("/environments/users/{userId}")
    public List<Environments> getEnvironmentsById(@PathVariable String userId) {
        return envRepository.findByUserId(userId);
    }

    @GetMapping("/environments/{envId}")
    public Environments getEnvironmentById(@PathVariable String envId) {
        return envRepository.findById(envId).get();
    }

    @PutMapping("environments/{envId}")
    public Environments updateEnvironment(@RequestBody Environments environment,  @PathVariable String envId) {
        Environments updatedEnv = envRepository.findById(envId).get();
        BeanUtils.copyProperties(environment, updatedEnv );
        envRepository.save(updatedEnv);
        return updatedEnv;
    }

    @PostMapping("environments")
    public String insertEnvironment(@RequestBody Environments environments) {
        envRepository.save(environments);
        return "successfully Inserted";
    }


    @DeleteMapping("environments/{envId}")
    public String deleteEnvironment(@PathVariable String envId) {
        Environments environment = envRepository.findById(envId).get();
        envRepository.delete(environment);
        return "successfully deleted";
    }

}
