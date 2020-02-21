package com.example.ot.otusers.service;

import com.example.ot.otusers.db.entity.QUser;
import com.example.ot.otusers.db.entity.User;
import com.example.ot.otusers.db.repository.UserRepository;
import com.example.ot.otusers.model.UserDTO;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final int PAGE_SIZE = 50;

    public Optional<UserDTO> findUser(long userId) {
        return userRepository.findById(userId).map(this::toDto);
    }

    public List<UserDTO> findByNamePrefix(int page, String prefix) {
        BooleanBuilder where = new BooleanBuilder(QUser.user.firstName.startsWith(prefix));
        where.or(QUser.user.lastName.startsWith(prefix));
        return userRepository
                .findAll(where, PageRequest.of(page, PAGE_SIZE))
                .getContent().stream()
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
