package com.silabo.entidades;

import java.time.LocalDateTime;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Silabo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_silabo")
    private Integer idSilabo;

    @Column(name = "nombre_documento_silabo", length = 100, unique = true)
    private String nombreDocumentoSilabo;

    @Column(name = "area_estudios", length = 50)
    private String areaEstudios;

    @Column(name = "descripcion_silabo", length = 10000)
    private String descripcionSilabo;

    @Column(name = "estrategia_didactica", columnDefinition = "TEXT")
    private String estrategiaDidactica;

    @Column(name = "bibliografia", columnDefinition = "TEXT")
    private String bibliografia;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_u_modificacion")
    private LocalDateTime fechaUModificacion;

    @Column(name = "nombre_completo_profesor", length = 100, nullable = false)
    private String nombreCompletoProfesor;

    @Column(name = "email_profesor", length = 100)
    private String emailProfesor;

    @Column(name = "cantidad_unidades")
    private Integer cantidadUnidades;

    @ManyToOne
    @JoinColumn(name = "cursoid", foreignKey = @ForeignKey(name = "FK_curso_silabo"))
    private Curso curso;
}
