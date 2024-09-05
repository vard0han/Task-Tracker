package com.example.tasktracker.CLI;

import com.example.tasktracker.models.Task;
import com.example.tasktracker.models.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.tasktracker.Service.taskService;

import java.util.Date;

@Component
public class TaskCLI implements CommandLineRunner {
    @Autowired
    private taskService taskService;
    private Task creatTask(){
        Task task = new Task();
        task.setStatus(status.Todo);
        task.setCreatedAt(new Date());
        task.setUpdatedAt(new Date());
        return task;
    }
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 0) {
            System.out.println("No arguments provided");
            return;
        }
        String action = args[0];
        switch (action) {
            case "add":
                if(args.length < 2) {
                    System.out.println("Please provide a description");
                    return;
                }
                Task task = creatTask();
                task.setDescription(args[1]);
                Long id = taskService.saveTask(task);
                System.out.println("Task created with id: " + id);
                break;
            case "update":
                if(args.length < 3) {
                    System.out.println("Please provide an id and a description");
                    return;
                }
                Task task2 = creatTask();
                task2.setId(Long.parseLong(args[1]));
                task2.setDescription(args[2]);
                taskService.updateTask(task2);
                break;
            case "delete":
                if(args.length < 2) {
                    System.out.println("Please provide an id");
                    return;
                }
                taskService.deleteTask(Long.parseLong(args[1]));
                break;
            case "done":
                if(args.length < 2) {
                    System.out.println("Please provide an id");
                    return;
                }
                taskService.markAsDone(Long.parseLong(args[1]));
                break;
            case "in-progress":
                if(args.length < 2) {
                    System.out.println("Please provide an id");
                    return;
                }
                taskService.markAsInProgress(Long.parseLong(args[1]));
                break;
            case "list":
                taskService.getTasks().forEach(task1 -> {
                    System.out.println(task1.getId() + " - " + task1.getDescription() + " - " + task1.getStatus());
                });
                break;
            case "list-by-status":
                if(args.length < 2) {
                    System.out.println("Please provide a status");
                    return;
                }
                taskService.getTasksByStatus(args[1]).forEach(task1 -> {
                    System.out.println(task1.getId() + " - " + task1.getDescription() + " - " + task1.getStatus());
                });
                break;
            default:
                System.out.println("Invalid action");
        }
    }
}
