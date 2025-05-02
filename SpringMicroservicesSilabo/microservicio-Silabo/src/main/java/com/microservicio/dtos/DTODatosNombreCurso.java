package com.microservicio.dtos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public class DTODatosNombreCurso {
    @Id
    Long id;
    @Column("codigo")
    String codigoCurso;
    @Column("nombre")
    String nombreCurso;
    @Column("plan_estudios")
    String planDeEstudios;
}
