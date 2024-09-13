package com.skyes.taskapp.controller;

import com.skyes.taskapp.entity.User;
import com.skyes.taskapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository mySqlRepository;

    @GetMapping
    public List<User> getUsers() {
        return mySqlRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return mySqlRepository.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable("id") Integer id) {
        if (!mySqlRepository.existsById(id)) {
            return false;
        }
        mySqlRepository.deleteById(id);
        return true;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Integer id, @RequestBody Map<String, String> body) {
        User current = mySqlRepository.findById(id).get();

        current.setUsername(body.get("username"));
        current.setAge(Integer.parseInt(body.get("age")));

        mySqlRepository.save(current);
        return current;
    }

    @PostMapping
    public User createUser(@RequestBody Map<String, String> body) {
        User user = new User();
        user.setUsername(body.get("username"));
        user.setAge(Integer.parseInt(body.get("age")));
        mySqlRepository.save(user);
        return user;
    }
}

