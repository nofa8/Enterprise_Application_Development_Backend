package pt.ipleiria.estg.dei.ei.dae.project.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class AuthDTO implements Serializable {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public AuthDTO() {
    }

    public AuthDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
