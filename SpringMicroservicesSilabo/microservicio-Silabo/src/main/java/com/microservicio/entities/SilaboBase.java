package com.microservicio.entities;

import io.asyncer.r2dbc.mysql.internal.NotNullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "silabo")
public class SilaboBase {
    @Id
    private Long idSilabo;
    @Column("nombre_documento")
    private String nombreDocumentoSilabo;
    private String areaEstudios;
    private String descripcionSilabo;
    private String estrategiaDidactica;
    private String bibliografia;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimafechaModificacion;
    private Long idCurso;
    private int cantidaUnidades;
    private String nombreCompletoDocente;
    private String emailDocente;

    public SilaboBase(String nombreDocumentoSilabo ) {
        this.nombreDocumentoSilabo = nombreDocumentoSilabo;
    }

    public SilaboBase(String areaEstudios,String nombreCompletoDocente, String emailDocente){
        this.areaEstudios=areaEstudios;
        this.nombreCompletoDocente=nombreCompletoDocente;
        this.emailDocente=emailDocente;
    }

    public void setNombreDocumentoSilabo(String nombreDocumentoSilabo) {
        this.nombreDocumentoSilabo = nombreDocumentoSilabo;
    }

    public void setAreaEstudios(String areaEstudios) {
        this.areaEstudios = areaEstudios;
    }

    public void setDescripcionSilabo(String descripcionSilabo) {
        this.descripcionSilabo = descripcionSilabo;
    }

    public void setEstrategiaDidactica(String estrategiaDidactica) {
        this.estrategiaDidactica = estrategiaDidactica;
    }

    public void setBibliografia(String bibliografia) {
        this.bibliografia = bibliografia;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public void setCantidaUnidades(int cantidaUnidades) {
        this.cantidaUnidades = cantidaUnidades;
    }

    public void setNombreCompletoDocente(String nombreCompletoDocente) {
        this.nombreCompletoDocente = nombreCompletoDocente;
    }

    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
    }
}




