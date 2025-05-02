package com.microservicio.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@Table(name = "curso")
public class CursoDatosNecesarios {
    @Id
    private Long idCurso;
    private String codigoCurso;
    private String nombreCurso;
    private String tipoCurso;
    private int numeroHorasTeoria;
    private int numeroHorasPractica;
    private String ciclo;
    @Setter private String sumilla;
    private float numeroCreditos;
    @Setter private String modalidad;
}
