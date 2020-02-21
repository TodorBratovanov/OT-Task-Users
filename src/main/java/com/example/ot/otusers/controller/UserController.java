package com.example.ot.otusers.controller;

import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        return userService
                .findUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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

    @GetMapping(value = "/prefix/{prefix}")
    public ResponseEntity<List<UserDTO>> searchByPrefix(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                        @PathVariable("prefix") String prefix) {
        List<UserDTO> byNamePrefix = userService.findByNamePrefix(page, prefix);
        return ResponseEntity.ok(byNamePrefix);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<List<UserDTO>> searchByEmail(@PathVariable("email") @Pattern(regexp = EMAIL_PATTERN) String email) {
        List<UserDTO> byEmail = userService.findByEmail(email);
        return ResponseEntity.ok(byEmail);
    }

}
