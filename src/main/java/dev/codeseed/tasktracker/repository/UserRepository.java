package dev.codeseed.tasktracker.repository;

import dev.codeseed.tasktracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
