package com.microservicio.services;

import com.microservicio.entities.CursoDatosNecesarios;
import reactor.core.publisher.Mono;

public interface ICursoDatosNecesariosServices {

    // metodo para obtener el curso por id

    Mono<CursoDatosNecesarios> encontrarCursoPorId(Long id);

    // guardar curso

    Mono<CursoDatosNecesarios> guardarDatosCursoIG(CursoDatosNecesarios curso);
}
