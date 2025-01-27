package com.hans.topics.dto;

import com.hans.topics.validation.UniqueEmail;
import com.hans.topics.validation.UniqueUsernameValInt;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    @Size(max = 100)
    @UniqueUsernameValInt(message = "has been taken")
    private String username;

    @NotBlank
    @Size(max = 100)
    @UniqueEmail(message = "has been taken")
    private String email;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String password;

}