package com.microservicio.dtos;

import lombok.Data;

@Data
public class DTODatosNombreCurso {
    Long id;
    String codigoCurso;
    String nombreCurso;
    String planDeEstudios;
}
