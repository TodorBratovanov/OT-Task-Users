package com.example.ot.otusers.service;

import com.example.ot.otusers.db.entity.User;
import com.example.ot.otusers.db.repository.UserRepository;
import com.example.ot.otusers.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findByNamePrefix(String prefix) {
        return userRepository
                .findByFirstNameStartsWithOrLastNameStartsWith(prefix, prefix).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findByEmail(String email) {
        return userRepository
                .findByEmail(email).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO create(UserDTO userDTO) {
        return toDto(userRepository.save(toEntity(userDTO)));
    }

    public Optional<UserDTO> update(long userId, UserDTO userDTO) {
        return userRepository
                .findById(userId)
                .map(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    return toDto(userRepository.save(user));
                });
    }

    public Optional<UserDTO> delete(long userId) {
        return userRepository
                .findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return toDto(user);
                });
    }

    private UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    private User toEntity(UserDTO dto) {
        return new User(
                null,
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail()
        );
    }

}
