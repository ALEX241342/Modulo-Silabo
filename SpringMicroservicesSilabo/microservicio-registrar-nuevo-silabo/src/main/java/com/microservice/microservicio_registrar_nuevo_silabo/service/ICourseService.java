package com.microservice.microservicio_registrar_nuevo_silabo.service;

import com.microservice.microservicio_registrar_nuevo_silabo.entity.Silabo1_IF;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICourseService {

    //metodos para generar silabo

    //guardar silabo
    Mono<Void> save(Silabo1_IF parteSilabo);

    //recupera por id (codigoDeCurso)
    Mono<Silabo1_IF> findById(Long idSilabo);


    // ultimos 5 para visualizar
    Flux<Silabo1_IF> findTop5Recents();





}
