package com.microservicio.services;

import com.microservicio.entities.CursoDatosNecesarios;
import com.microservicio.entities.Logro;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.microservicio.entities.Competencia;

public interface ICursoDatosNecesariosServices {

    // metodo para obtener el curso por id

    Mono<CursoDatosNecesarios> encontrarCursoPorId(Long id);

    // guardar curso

    Mono<CursoDatosNecesarios> guardarDatosCursoIG(CursoDatosNecesarios curso);

    // obtener competencias por curso

    Flux<Competencia> obtenerCompetenciasPorCurso(Long idCurso);

    // obtener logros por curso y competencia

    Flux<Logro> obtenerLogrosPorCursoYCompetencia(Long idCurso, Long idCompetencia);
}
