package com.microservicio.repositories;

import java.util.List;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.microservicio.entities.Logro;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface LogroRepositorio extends R2dbcRepository<Logro, Long> {

    @Query("""
        SELECT l.id_logro, l.codigo_logro, l.descripcion_logro, l.id_competencia
        FROM logro l
        JOIN cursocompetencia cc ON l.id_competencia = cc.id
        WHERE cc.cursoid = :cursoId AND cc.competenciaid = :competenciaId
    """)
    Flux<Logro> findByCursoAndCompetencia(Long cursoId, Long competenciaId);

    default Mono<Logro> guardarLogro(String codigoLogro, String descripcionLogro, Long idCompetencia) {
        return save(new Logro(codigoLogro, descripcionLogro, idCompetencia));
    }

    default Mono<Void> eliminarPorId(Long idLogro) {
        return deleteById(idLogro);
    }

    default Flux<Logro> guardarVariosLogros(List<Logro> logros, Long idSilabo, Long idCompetencia) {
        logros.forEach(l -> {
            l.setId(idSilabo);
            l.setIdCursocompetencia(idCompetencia);
        });
        return saveAll(logros);
    }

    default Mono<Void> eliminarVariosLogros(List<Long> idsLogros) {
        return deleteAllById(idsLogros);
    }

    

}