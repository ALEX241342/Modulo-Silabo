package com.microservicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinimoSilabo {

    private Long idSilabo;
    private String codigoSilabo;
    private Long idCurso;
    private String areaEstudios;
    private Long idUnidad;
    private Long idPeriodo;
    private Long codigoPrerrequisito;
    private String estrategiaDidactica;
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimafechaModificacion;

}
