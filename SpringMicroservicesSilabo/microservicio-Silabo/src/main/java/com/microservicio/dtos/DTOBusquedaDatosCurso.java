package com.microservicio.dtos;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class DTOBusquedaDatosCurso {
    String codigoCurso;
    String nombreCurso;
    String numeroPeriodo;
    String anio;
    String ciclo;
    BigDecimal numeroDeCreditos;
    String planDeEstudios;
    String tipoDeCurso;
}
