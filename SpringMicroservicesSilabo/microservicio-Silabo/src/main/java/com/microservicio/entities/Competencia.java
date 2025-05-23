package com.microservicio.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("competencia")
public class Competencia {
    @Id
    @Column("id")
    private Long id;
    @Column("codigo")
    private String codigo;
    @Column("descripcion")
    private String descripcion;
    @Column("nombre")
    private String nombre;
}
