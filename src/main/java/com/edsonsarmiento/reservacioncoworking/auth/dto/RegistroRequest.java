package com.edsonsarmiento.reservacioncoworking.auth.dto;

import com.edsonsarmiento.reservacioncoworking.auth.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroRequest(
        @NotBlank(message = "El campo email no puede esta vacío")
        @Email(message = "El formato del email no es valido")
        @NotNull(message = "El campo email es obligatorio")
        String email,

        @NotBlank(message = "El campo password no puede estar vacío")
        @NotNull(message = "El campo password es obligatorio")
        String password,

        @NotNull(message = "El campo role es obligatorio")
        Role role
) {
}
