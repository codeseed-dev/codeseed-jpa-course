package dev.codeseed.tasktracker.controller;

import dev.codeseed.tasktracker.model.Task;
import dev.codeseed.tasktracker.model.TaskStatus;
import dev.codeseed.tasktracker.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Page<Task> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            Pageable pageable
    ) {
        return (status != null)
                ? taskService.getByStatus(status, pageable)
                : taskService.getAll(pageable);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable Long userId) {
        return taskService.getByUserId(userId);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String keyword) {
        return taskService.searchByTitle(keyword);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}