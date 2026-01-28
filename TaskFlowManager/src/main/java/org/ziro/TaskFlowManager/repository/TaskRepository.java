package org.ziro.TaskFlowManager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ziro.TaskFlowManager.model.Task;
import org.ziro.TaskFlowManager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task getTasksByTitle(String title);
    Task getTasksById(Long taskId);
    List<Task> findByUser(User user);
    List<Task> getTasksByUser(User user);
    Boolean existsById(Long taskId);
    Optional<Task> findById(Long taskId);
}
