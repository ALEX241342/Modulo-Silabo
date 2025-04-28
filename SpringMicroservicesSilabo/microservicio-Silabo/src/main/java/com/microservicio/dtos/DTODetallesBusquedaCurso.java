package com.microservicio.dtos;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class DTODetallesBusquedaCurso {
    Long id;
    String codigoCurso;
    String nombreCurso;
    String anio;
    String numeroPeriodo;
    String ciclo;
    BigDecimal numeroDeCreditos;
    String planDeEstudios;
    String tipoDeCurso;
}
