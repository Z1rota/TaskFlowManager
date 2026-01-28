package org.ziro.TaskFlowManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.ziro.TaskFlowManager.model.Task;
import org.ziro.TaskFlowManager.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;


    private String getCurrentUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getAllTasksByUsername(getCurrentUsername());
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.createTask(getCurrentUsername(), task);
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(getCurrentUsername(), id);
    }


}   
