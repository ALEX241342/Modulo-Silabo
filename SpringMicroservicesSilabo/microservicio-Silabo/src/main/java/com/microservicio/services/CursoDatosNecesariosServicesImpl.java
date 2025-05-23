package com.microservicio.services;

import com.microservicio.entities.Competencia;
import com.microservicio.entities.CursoDatosNecesarios;
import com.microservicio.entities.Logro;
import com.microservicio.repositories.CompetenciaRepository;
import com.microservicio.repositories.CursoDatosNecesariosRepository;
import com.microservicio.repositories.LogroRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
public class CursoDatosNecesariosServicesImpl implements ICursoDatosNecesariosServices{

    @Autowired
    CursoDatosNecesariosRepository cursoRepository;

    @Autowired
    CompetenciaRepository competenciaRepository;

    @Autowired
    LogroRepositorio logroRepository;

    @Override
    public Mono<CursoDatosNecesarios> encontrarCursoPorId(Long idCurso) {
        return cursoRepository.findById(idCurso);
    }

    @Override
    public Mono<CursoDatosNecesarios> guardarDatosCursoIG(CursoDatosNecesarios curso){
        return encontrarCursoPorId(curso.getIdCurso())
                .flatMap(cursoRecuperado -> {
                    cursoRecuperado.setModalidad(curso.getModalidad());
                    return cursoRepository.save(cursoRecuperado);
                });
    }

    @Override
    public Flux<Competencia> obtenerCompetenciasPorCurso(Long idCurso) {
        return competenciaRepository.findByCursoId(idCurso)
            .doOnNext(competencia -> {
                System.out.println("Competencia encontrada: " + 
                    "ID: " + competencia.getId() + 
                    ", Código: " + competencia.getCodigo() + 
                    ", Descripción: " + competencia.getDescripcion() + 
                    ", Nombre: " + competencia.getNombre());
            })
            .timeout(Duration.ofSeconds(5))
            .onErrorResume(e -> {
                System.err.println("Error al obtener competencias: " + e.getMessage());
                return Flux.empty();
            });
    }

    @Override
    public Flux<Logro> obtenerLogrosPorCursoYCompetencia(Long idCurso, Long idCompetencia) {
        return logroRepository.findByCursoAndCompetencia(idCurso, idCompetencia)
            .doOnNext(logro -> {
                System.out.println("Logro encontrado: " + 
                    "ID: " + logro.getId() + 
                    ", Código: " + logro.getCodigoLogro() + 
                    ", Descripción: " + logro.getDescripcionLogro());
            })
            .timeout(Duration.ofSeconds(5))
            .onErrorResume(e -> {
                System.err.println("Error al obtener logros: " + e.getMessage());
                return Flux.empty();
            });
    }
    
    
}
