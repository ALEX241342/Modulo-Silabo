package com.microservicio.services;

import com.microservicio.dtos.DTOBusquedaDatosCurso;
import com.microservicio.dtos.DTODatosNombreCurso;
import com.microservicio.entities.MinimoCurso;
import com.microservicio.repositories.BusquedaCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BusquedaCursoServiceImpl implements BusquedaCursoService {

    @Autowired
    BusquedaCursoRepository busquedaCursoRepository;

    @Override
    public Mono<DTOBusquedaDatosCurso> buscarPorCodigoCurso(String codigoCurso) {
        return busquedaCursoRepository.buscarPorCodigoCurso(codigoCurso);
    }

    @Override
    public Flux<DTODatosNombreCurso> buscarPorNombreCursos(String parteNombreCurso) {
        return busquedaCursoRepository.listarOpcionesPorNombreCurso(parteNombreCurso);
    }

    @Override
    public Mono<String> buscarCodigoPorNombre(Long id) {
        return busquedaCursoRepository.buscarCodigoPorNombre(id);
    }

}
