package com.microservicio.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@Table(name = "curso")
public class CursoDatosNecesarios {
    @Id
    private Long idCurso;
    @Column("codigo")
    private String codigoCurso;
    @Column("nombre")
    private String nombreCurso;
    @Column("tipo")
    private String tipoCurso;
    @Column("numHorasTeoria")
    private int numeroHorasTeoria;
    @Column("numHorasPractica")
    private int numeroHorasPractica;
    @Column("ciclo")
    private String ciclo;
    @Column("sumilla")
    @Setter private String sumilla;
    @Column("numCreditos")
    private float numeroCreditos;
    @Column("modalidad")
    @Setter private String modalidad;

    public CursoDatosNecesarios(String modalidad) {
        this.modalidad = modalidad;
    }
}
