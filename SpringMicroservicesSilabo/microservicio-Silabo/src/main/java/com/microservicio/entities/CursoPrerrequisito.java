package com.microservicio.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@Table(name = "cursoprerrequisito")
public class CursoPrerrequisito {
    private Long idPrerrequisito;
    private Long idSilabo;
    @Setter private String codigoCurso;
    @Setter private String nombreCurso;
}
