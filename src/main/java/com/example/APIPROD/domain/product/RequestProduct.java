package com.example.APIPROD.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestProduct(@NotBlank String name, @NotNull Integer price_in_cents) {
    
}
