package com.example.ot.otusers.controller;

import com.example.ot.otusers.model.RdfFormat;
import com.example.ot.otusers.model.UserDTO;
import com.example.ot.otusers.service.RdfService;
import com.example.ot.otusers.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class UserController {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String DEFAULT_FILE_NAME = "all-users";

    private final UserService userService;
    private final RdfService rdfService;

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

    @GetMapping("/download/rdf")
    public StreamingResponseBody downloadRdfCustom(HttpServletResponse response,
                                                   @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName,
                                                   @RequestParam RdfFormat format) {
        response.setContentType(format.getMediaType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + format.getFileExtension());

        List<UserDTO> allUsers = userService.findAll();
        return outputStream -> rdfService.generateRdf(allUsers, outputStream, format);
    }

}
