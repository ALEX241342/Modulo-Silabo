package com.microservicio.services;

import com.microservicio.dtos.DTOBusquedaDatosCurso;
import com.microservicio.dtos.DTODatosNombreCurso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBusquedaCursoServices {

    //metodos para busqueda de cursos (GS)

    // buscar curso por codigo

    Mono<DTOBusquedaDatosCurso> buscarPorCodigoCurso(String codigoCurso);

    // buscar curso por nombre (autocompletado dinamico front)

    Flux<DTODatosNombreCurso> buscarPorNombreCursos(String nombreCurso);

    Mono<String> buscarCodigoPorNombre(Long id);
}
