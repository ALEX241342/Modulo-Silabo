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
    @Column("area_estudios")
    @Setter private String areaEstudios;
    @Column("descripcion_silabo")
    @Setter private String descripcionSilabo;
    @Column("estrategia_didactica")
    @Setter private String estrategiaDidactica;
    @Column("bibliografia")
    @Setter private String bibliografia;
    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column("fecha_u_modificacion")
    private LocalDateTime ultimafechaModificacion;

    @Column("cursoid")
    @Setter private Long idCurso;
    @Column("cantidad_unidades")
    @Setter private int cantidadUnidades;
    @Column("nombre_completo_profesor")
    @Setter private String nombreCompletoDocente;
    @Column("email_profesor")
    @Setter private String emailDocente;

    public SilaboBase(String nombreDocumentoSilabo, Long idCurso) {
        this.nombreDocumentoSilabo = nombreDocumentoSilabo;
        this.idCurso = idCurso;
    }

}




