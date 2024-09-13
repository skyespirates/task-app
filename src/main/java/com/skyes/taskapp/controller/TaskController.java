package com.skyes.taskapp.controller;

import com.skyes.taskapp.entity.Task;
import com.skyes.taskapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Task task = optionalTask.get();
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            String message = "Task with id " + id + " not found";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        taskRepository.delete(optionalTask.get());
        return new ResponseEntity<>("Task with id " + id + " deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        // check whether task with given id exists or not
        Optional<Task> optionalTask = taskRepository.findById(id);

        // if it didn't exist give response 404 not found
        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // if exists, check which key is provided by request body
        // if and only if key exists update given key
        Task task = optionalTask.get();
        if(body.containsKey("task")) {
            task.setTask(body.get("task"));
        }
        if(body.containsKey("status")) {
            try {
                task.setStatus(Boolean.parseBoolean(body.get("status")));
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Map<String, String> body) {
        Task task = new Task();
        if(!body.containsKey("task")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        task.setTask(body.get("task"));
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }
}
