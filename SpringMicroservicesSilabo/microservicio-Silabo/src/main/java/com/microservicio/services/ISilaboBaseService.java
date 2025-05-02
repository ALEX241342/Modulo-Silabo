package com.microservicio.services;

import com.microservicio.entities.SilaboBase;
import reactor.core.publisher.Mono;

public interface ISilaboBaseService {

    //metodos para editar silabo

    //recuperar silabo por nombre
    Mono<SilaboBase> obtenerSilaboPorNombre(String nombreSilabo);

    // guardar Informacion General Silabo
    Mono<SilaboBase> guardarSilaboIG(Long id, String areaDeEstudio, String nombreDocente, String emailDocente);

    Mono<SilaboBase> asignarNombreAlNuevoSilabo(String nombreDocumento);

}
