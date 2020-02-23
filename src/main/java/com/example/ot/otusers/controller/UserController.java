package com.example.ot.otusers.controller;

import com.example.ot.otusers.model.RdfFormat;
import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.service.RdfService;
import com.example.ot.otusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String DEFAULT_FILE_NAME = "all-users";

    private final UserService userService;
    private final RdfService rdfService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        return userService
                .findUser(id)
                .map(user -> {
                    log.info("User with id: {} found - {} {}", id, user.getFirstName(), user.getLastName());
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> notFound("id", id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid @NotNull UserDTO user) {
        UserDTO created = userService.create(user);
        log.info("Created user: {} {}", created.getFirstName(), created.getLastName());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid @NotNull UserDTO user) {
        return userService
                .update(id, user)
                .map(updated -> {
                    log.info("User with id: {} updated - {} {}", id, updated.getFirstName(), updated.getLastName());
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> notFound("id", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        return userService
                .delete(id)
                .map(deleted -> {
                    log.info("User with id: {} deleted - {} {}", id, deleted.getFirstName(), deleted.getLastName());
                    return ResponseEntity.ok(deleted);
                })
                .orElseGet(() -> notFound("id", id));
    }

    @GetMapping(value = "/prefix/{prefix}")
    public ResponseEntity<List<UserDTO>> searchByPrefix(@RequestParam(defaultValue = "0") @Min(0) Integer page,
                                                        @PathVariable("prefix") String prefix) {
        List<UserDTO> byNamePrefix = userService.findByNamePrefix(page, prefix);
        log.info("Found {} users with name prefix: {}", byNamePrefix.size(), prefix);
        return ResponseEntity.ok(byNamePrefix);
    }

    @GetMapping(value = "/email/{email}")
    public ResponseEntity<UserDTO> searchByEmail(@PathVariable("email") @Pattern(regexp = EMAIL_PATTERN) String email) {
        return userService.findByEmail(email)
                .map(byEmail -> {
                    log.info("User with email: {} found - {} {}", email, byEmail.getFirstName(), byEmail.getLastName());
                    return ResponseEntity.ok(byEmail);
                })
                .orElseGet(() -> notFound("email", email));
    }

    @GetMapping("/download/rdf")
    public StreamingResponseBody downloadRdfCustom(HttpServletResponse response,
                                                   @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName,
                                                   @RequestParam RdfFormat format) {
        response.setContentType(format.getMediaType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + format.getFileExtension());

        List<UserDTO> allUsers = userService.findAll();
        log.info("Start downloading RDF file for {} users", allUsers.size());
        return outputStream -> rdfService.generateRdf(allUsers, outputStream, format);
    }

    private ResponseEntity<UserDTO> notFound(String criteria, Object id) {
        log.warn("User with {}: {} not found", criteria, id);
        return ResponseEntity.notFound().build();
    }

}
