package dev.codeseed.tasktracker.service;


import dev.codeseed.tasktracker.dto.UserDto;
import dev.codeseed.tasktracker.exception.ResourceNotFoundException;
import dev.codeseed.tasktracker.model.User;
import dev.codeseed.tasktracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return toDto(user);
    }

    public UserDto createUser(UserDto dto) {
        User user = toEntity(dto);
        User saved = userRepository.save(user);
        return toDto(saved);
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setDepartment(dto.getDepartment());
        user.setActive(dto.getActive());

        return toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setDepartment(dto.getDepartment());
        user.setActive(dto.getActive());
        return user;
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getDepartment(),
                user.getActive()
        );
    }
}
