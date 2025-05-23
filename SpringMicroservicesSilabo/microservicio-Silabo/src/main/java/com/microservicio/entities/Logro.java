package com.microservicio.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor 
@Table("logro")
public class Logro {

    @Id
    @Column("id_logro")
    @Setter private Long id;

    @Column("codigo_logro")
    @Setter private String codigoLogro;

    @Column("descripcion_logro")
    @Setter private String descripcionLogro;

    @Column("id_competencia")
    @Setter private Long idCursocompetencia;

    public Logro(String codigoLogro, String descripcionLogro, Long idCompetencia) {
        this.codigoLogro = codigoLogro;
        this.descripcionLogro = descripcionLogro;
        this.idCursocompetencia = idCompetencia;
    }
}
