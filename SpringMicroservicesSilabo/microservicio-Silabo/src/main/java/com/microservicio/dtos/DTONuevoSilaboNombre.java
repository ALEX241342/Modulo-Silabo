package com.microservicio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTONuevoSilaboNombre(
        @NotBlank String nombreDocumento,
        @NotNull Long idCurso
) {
}
