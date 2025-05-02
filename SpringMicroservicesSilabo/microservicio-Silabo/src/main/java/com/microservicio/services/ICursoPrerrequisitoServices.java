package com.microservicio.services;

import com.microservicio.entities.CursoPrerrequisito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICursoPrerrequisitoServices {

    // guardar curso prerrequisito
    Mono<CursoPrerrequisito> guardarCursoPreRequisito(CursoPrerrequisito cursoPrerrequisito);

    //recuperar cursos prerrequisito

    Flux<CursoPrerrequisito> recuperarCursosPreRequisito(Long idSilabo);


}
