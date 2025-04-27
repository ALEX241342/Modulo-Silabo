package com.microservice.microservicio_registrar_nuevo_silabo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Ta
public class MinimoCurso {

    private Long idCurso;
    private String codigoCurso;
    private String nombreCurso;
    private String tipoCurso;
    private int numeroHorasTeoria;
    private int numeroHorasPractica;
    private String ciclo;
    private String sumilla;
    private float numeroCreditos;
    private String modalidad;

}
