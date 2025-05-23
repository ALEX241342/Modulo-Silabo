package com.microservicio.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "cursoprerrequisito")
public class CursoPrerrequisito {
    @Id
    private Long idPrerrequisito;
    @Column("id_silabo")
    @Setter private Long idSilabo;
    @Column("codigo_curso")
    @Setter private String codigoCurso;
    @Column("nombre_curso")
    @Setter private String nombreCurso;

    public CursoPrerrequisito(Long idSilabo, String codigoCurso, String nombreCurso) {
        this.idSilabo = idSilabo;
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
    }
}



