package com.microservicio.dtos;


import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;


@Data
public class DTOBusquedaDatosCurso {
    @Column("codigo")
    private String codigoCurso;
    @Column("nombre")
    private String nombreCurso;
    @Column("numero_periodo")
    private String numeroPeriodo;
    @Column("año")
    private String anio;
    @Column("ciclo")
    private String ciclo;
    @Column("creditos")
    private BigDecimal numeroDeCreditos;
    @Column("plan_estudios")
    private String planDeEstudios;
    @Column("tipo")
    private String tipoDeCurso;
}
