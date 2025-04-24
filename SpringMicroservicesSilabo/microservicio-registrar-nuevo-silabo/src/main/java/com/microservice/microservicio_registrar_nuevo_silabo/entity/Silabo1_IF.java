package com.microservice.microservicio_registrar_nuevo_silabo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Table("silabo_IF")
public class Silabo1_IF {
    @Id
    private Long idSilabo;
    private int seccionSilabo;
    private String nombreCurso;
    private String codigoCurso;
    private String tipoCurso; //enum?
    private String areaDeEstudios;
    private int numeroSemanas;
    private float horasSemanales;
    //Semestre academico
    private String Anio;
    private String periodo; //enum?
    private String ciclo;
    private int creditos;
    private String modalidad;
    private List<MinCurso> prerrequisitos;
    private String nombreDocente;

}
