package com.microservicio.services;
import com.microservicio.entities.CursoDatosNecesarios;
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
    public Mono<CursoDatosNecesarios> buscarPorCodigoCurso(String codigoCurso) {
        return busquedaCursoRepository.buscarPorCodigoCurso(codigoCurso);
    }

    @Override
    public Flux<CursoDatosNecesarios> buscarPorNeCursos(String parteNombreCurso) {
        return busquedaCursoRepository.listarOpcionesPorNombreCurso(parteNombreCurso)
            .doOnNext(curso -> {
                System.out.println("Curso encontrado: " + curso);
                System.out.println("ID del curso: " + curso.getIdCurso());
            });
    }

    @Override
    public Mono<String> buscarCodigoPorNombre(Long id) {
        return busquedaCursoRepository.buscarCodigoPorNombre(id);
    }

}