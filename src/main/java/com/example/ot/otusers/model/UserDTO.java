package com.example.ot.otusers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.example.ot.otusers.controller.UserController.EMAIL_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    private Long id;

    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = "^[A-Za-z -]+$", message = "Must contain latin letters, space or hyphen")
    @JsonProperty("first-name")
    private String firstName;

    @NotEmpty
    @Size(max = 40)
    @Pattern(regexp = "^[A-Za-z -]+$", message = "Must contain latin letters, space or hyphen")
    @JsonProperty("last-name")
    private String lastName;

    @NotEmpty
    @Size(max = 50)
    @Pattern(regexp = EMAIL_PATTERN, message = "Not a valid email address")
    @JsonProperty("e-mail")
    private String email;

}
