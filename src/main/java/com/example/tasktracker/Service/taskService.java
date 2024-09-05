package com.example.tasktracker.Service;

import com.example.tasktracker.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

public interface taskService {
    Long saveTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);
    void markAsDone(Long id);
    void markAsInProgress(Long id);
    List<Task> getTasks();
    List<Task> getTasksByStatus(String status);

}
