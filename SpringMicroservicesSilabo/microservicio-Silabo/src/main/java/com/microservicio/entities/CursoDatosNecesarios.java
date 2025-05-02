package com.microservicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String sumilla;
    private float numeroCreditos;
    private String modalidad;
}
