package com.example.tasktracker.repository;

import com.example.tasktracker.models.Task;
import com.example.tasktracker.models.status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class taskRepository {
    private static final String Task_File = "tasks.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public Long saveTask(Task task) {
        try {
            List<Task> tasks = getTasks();
            task.setId(generateId(tasks));
            tasks.add(task);
            mapper.writeValue(new File(Task_File), tasks);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save task");
        }
        return task.getId();
    }
    private Long generateId(List<Task> tasks) {
        tasks.sort(Comparator.comparingLong(Task::getId));
        long expectedId = 1;
        for (Task task : tasks) {
            if (task.getId() != expectedId) {
                return expectedId;
            }
            expectedId++;
        }
        return expectedId;
    }

    public List<Task> getTasks() {
        try {
            File file = new File(Task_File);
            if(!file.exists()) {
                return new ArrayList<>();
            }
            return mapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read tasks");
        }
    }

    public void update(Task task) {
        try{
            List<Task> tasks = getTasks();
            tasks.removeIf(t -> t.getId().equals(task.getId()));
            tasks.add(task);
            mapper.writeValue(new File(Task_File), tasks);
        }catch (IOException e) {
            throw new RuntimeException("Failed to update task");
        }
    }

    public void delete(Long id) {
        try{
            List<Task> tasks = getTasks();
            tasks.removeIf(task -> task.getId().equals(id));
            mapper.writeValue(new File(Task_File), tasks);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete task");
        }
    }

    private Task getTask(Long id) {
        return getTasks().stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    public void markAsDone(Long id) {
        Task task = getTask(id);
        if(task != null){
            task.setStatus(status.Done);
            update(task);
        }
    }

    public void markAsInProgress(Long id) {
        Task task = getTask(id);
        if(task != null){
            task.setStatus(status.InProgress);
            update(task);
        }
    }
}
