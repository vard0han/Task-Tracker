package com.example.tasktracker.Service.Impl;

import com.example.tasktracker.Service.taskService;
import com.example.tasktracker.models.Task;
import com.example.tasktracker.repository.taskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class taskServiceImpl implements taskService {
    @Autowired
    private taskRepository taskRepository;

    @Override
    public Long saveTask(Task task) {
        return taskRepository.saveTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.delete(id);
    }

    @Override
    public void markAsDone(Long id) {
        taskRepository.markAsDone(id);
    }

    @Override
    public void markAsInProgress(Long id) {
        taskRepository.markAsInProgress(id);
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.getTasks();
    }

    @Override
    public List<Task> getTasksByStatus(String status) {
        List<Task> tasks = getTasks();
        tasks.removeIf(task -> !task.getStatus().name().equals(status));
        return tasks;
    }
}
