package com.microservicio.services.serviceBusquedaCurso;

import com.microservicio.dtos.DTOBusquedaDatosCurso;
import com.microservicio.dtos.DTODatosNombreCurso;
import com.microservicio.repositories.BusquedaCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IBusquedaCursoServicesImpl implements IBusquedaCursoServices {

    @Autowired
    private BusquedaCursoRepository busquedaCursoRepository;

    @Override
    public Mono<DTOBusquedaDatosCurso> buscarPorCodigoCurso(String codigoCurso) {
        return busquedaCursoRepository.buscarPorCodigoCurso(codigoCurso);
    }

    @Override
    public Flux<DTODatosNombreCurso> buscarPorNombreCursos(String parteNombreCurso) {
        return busquedaCursoRepository.listarOpcionesPorNombreCurso(parteNombreCurso)
                .log(); // Registra todos los eventos del Flux
    }

    @Override
    public Mono<String> buscarCodigoPorNombre(Long id) {
        return busquedaCursoRepository.buscarCodigoPorNombre(id);
    }

}
