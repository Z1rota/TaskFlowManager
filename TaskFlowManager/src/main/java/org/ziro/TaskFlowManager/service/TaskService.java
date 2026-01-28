package org.ziro.TaskFlowManager.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ziro.TaskFlowManager.model.User;
import org.ziro.TaskFlowManager.repository.TaskRepository;
import org.ziro.TaskFlowManager.model.Task;
import org.ziro.TaskFlowManager.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;



    public Task createTask(String username, Task task) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasksByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public void deleteTask(String username, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You cannot delete data of another user!");
        }

        taskRepository.delete(task);
    }

    public void update(Task task) {
        if(taskRepository.existsById(task.getId())) {
            taskRepository.save(task);
        }
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int id) {
        if ( id >0 || taskRepository.existsById(id)) {
            return taskRepository.findById(id).get();
        }
        return null;
    }

    public Task getTaskByTitle(String title) {
        return taskRepository.getTasksByTitle(title);
    }

    public Task getTaskById(String id) {
        return taskRepository.getTasksById(Long.parseLong(id));
    }

    public void deleteTaskById(int id) {
        taskRepository.deleteById(id);
    }
}
