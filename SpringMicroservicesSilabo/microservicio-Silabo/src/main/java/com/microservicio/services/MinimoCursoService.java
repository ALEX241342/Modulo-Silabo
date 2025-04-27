package com.microservicio.services;

import com.microservicio.entities.MinimoCurso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MinimoCursoService {

    //metodos para crear o generar un nuevo silabo (GS)

    // buscar curso por codigo

    Mono<MinimoCurso> buscarPorCodigoCurso(String codigoCurso);

    // buscar curso por nombre (autocompletado dinamico front)

    Flux<MinimoCurso> buscarPorNombreCursos(String nombreCurso);

    //guardar datos introducidos de silabo en curso

    Mono<Void> guardarDatosEnCurso(MinimoCurso minimoCurso);

}
