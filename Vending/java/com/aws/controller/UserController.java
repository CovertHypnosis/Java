package com.aws.controller;

import com.aws.Repository.UserRepository;
import com.aws.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("dynamodb")
public class UserController {
    @Autowired
    private UserRepository repository;

    @PostMapping("users")
    public String insertUser(@RequestBody User user) {
        repository.save(user);
        return "successfully Inserted";
    }


    @GetMapping("users/{id}")
    public User getUser(@PathVariable String id) {
        return repository.findById(id).get();
    }

    @GetMapping("users")
    public Iterable<User> getUsers() {
        return repository.findAll();
    }

    @PutMapping("users/{id}")
    public User updateUser(@RequestBody User user,  @PathVariable String id) {
        User updatedUser = repository.findById(id).get();
        BeanUtils.copyProperties(user, updatedUser);
        repository.save(updatedUser);
        return updatedUser;
    }

    @DeleteMapping("users/{id}")
    public String deleteUser(@PathVariable String id) {
        User user = repository.findById(id).get();
        repository.delete(user);
        return "successfully deleted";
    }
}
