package com.microservicio.entities;

import io.asyncer.r2dbc.mysql.internal.NotNullByDefault;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "silabo")
public class SilaboBase {
    @Id
    private Long idSilabo;
    @Column("nombre_documento")
    @Setter private String nombreDocumentoSilabo;
    @Setter private String areaEstudios;
    @Setter private String descripcionSilabo;
    @Setter private String estrategiaDidactica;
    @Setter private String bibliografia;

    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimafechaModificacion;

    @Column("cursoid")
    @Setter private Long idCurso;
    @Setter private int cantidaUnidades;
    @Setter private String nombreCompletoDocente;
    @Setter private String emailDocente;

    public SilaboBase(String nombreDocumentoSilabo, Long idCurso ) {
        this.nombreDocumentoSilabo = nombreDocumentoSilabo;
        this.idCurso = idCurso;
    }

}




