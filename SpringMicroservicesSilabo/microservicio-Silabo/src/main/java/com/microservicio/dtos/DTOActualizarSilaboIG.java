package com.microservicio.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DTOActualizarSilaboIG(
      @NotBlank String modalidad,
      @NotBlank String nombreCompletoDocente,
      @Email String emailDocente
) {
}
