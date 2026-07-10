package com.edsonsarmiento.reservacioncoworking.auth.dto;

import com.edsonsarmiento.reservacioncoworking.auth.Role;

public record RegistroRequest(
        String email,
        String password,
        Role role
) {
}
