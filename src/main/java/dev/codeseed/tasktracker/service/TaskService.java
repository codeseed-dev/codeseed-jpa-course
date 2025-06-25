package dev.codeseed.tasktracker.service;

import dev.codeseed.tasktracker.model.Task;
import dev.codeseed.tasktracker.model.TaskStatus;
import dev.codeseed.tasktracker.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Page<Task> getByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable);
    }

    public List<Task> getByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> searchByTitle(String keyword) {
        return taskRepository.searchByTitle(keyword);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDeadline(updatedTask.getDeadline());
            task.setStatus(updatedTask.getStatus());
            task.setUser(updatedTask.getUser());
            return taskRepository.save(task);
        });
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
