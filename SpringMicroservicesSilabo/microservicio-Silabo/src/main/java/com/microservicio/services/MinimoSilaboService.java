package com.microservicio.services;

import com.microservicio.entities.MinimoSilabo;
import reactor.core.publisher.Mono;

public interface MinimoSilaboService {

    //metodos para editar silabo

    //recuperar silabo por nombre
    Mono<MinimoSilabo> obtenerSilaboPorNombre(String nombreSilabo);

    // guardar silabo
    Mono<Void> guardarSilabo(MinimoSilabo minimoSilabo);


}
