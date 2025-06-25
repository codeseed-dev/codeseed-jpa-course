package dev.codeseed.tasktracker.repository;

import dev.codeseed.tasktracker.model.Task;
import dev.codeseed.tasktracker.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    List<Task> findByUserId(Long userId);

    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Task> searchByTitle(@Param("keyword") String keyword);

    @NonNull
    Page<Task> findAll(@NonNull Pageable pageable);
}
