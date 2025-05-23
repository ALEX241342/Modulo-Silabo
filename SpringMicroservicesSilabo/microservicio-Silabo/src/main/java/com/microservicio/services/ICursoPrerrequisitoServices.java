package com.microservicio.services;

import java.util.List;

import com.microservicio.entities.CursoPrerrequisito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICursoPrerrequisitoServices {

    // guardar curso prerrequisito
    Mono<CursoPrerrequisito> guardarCursoPreRequisito(CursoPrerrequisito cursoPrerrequisito);

    //recuperar cursos prerrequisito
    Flux<CursoPrerrequisito> recuperarCursosPreRequisito(Long idSilabo);

    //guardar varios prerrequisitos
    Flux<CursoPrerrequisito> guardarVariosPrerrequisitos(Long idSilabo, List<CursoPrerrequisito> prerrequisitos);

    //eliminar varios prerrequisitos
    Mono<Void> eliminarVariosPrerrequisitos(List<Long> idsPrerrequisitos);
}
