package com.example.ot.otusers.controller;

import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid @NotNull UserDTO user) {
        UserDTO created = userService.create(user);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid @NotNull UserDTO user) {
        return userService
                .update(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return userService
                .delete(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/searchBy", params = "prefix")
    public ResponseEntity<List<UserDTO>> searchByPrefix(@RequestParam("prefix") String prefix) {
        List<UserDTO> byNamePrefix = userService.findByNamePrefix(prefix);
        return ResponseEntity.ok(byNamePrefix);
    }

    @GetMapping(value = "/searchBy", params = "email")
    public ResponseEntity<List<UserDTO>> searchByEmail(@RequestParam("email") @Pattern(regexp = EMAIL_PATTERN) String email) {
        List<UserDTO> byEmail = userService.findByEmail(email);
        return ResponseEntity.ok(byEmail);
    }

}
