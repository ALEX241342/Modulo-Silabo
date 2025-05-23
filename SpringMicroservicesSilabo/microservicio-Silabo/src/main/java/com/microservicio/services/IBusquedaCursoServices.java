package com.microservicio.services;
import com.microservicio.entities.CursoDatosNecesarios;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBusquedaCursoServices {

    //metodos para busqueda de cursos (GS)

    // buscar curso por codigo

    Mono<CursoDatosNecesarios> buscarPorCodigoCurso(String codigoCurso);

    // buscar curso por nombre (autocompletado dinamico front)

    Flux<CursoDatosNecesarios> buscarPorNeCursos(String nombreCurso);

    Mono<String> buscarCodigoPorNombre(Long id);
}
