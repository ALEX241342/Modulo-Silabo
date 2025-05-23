package com.microservicio.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.microservicio.entities.Competencia;

import reactor.core.publisher.Flux;

public interface CompetenciaRepository extends R2dbcRepository<Competencia,Long>{

    @Query("""
        SELECT DISTINCT 
            c.id,
            c.codigo,
            c.descripcion,
            c.nombre
        FROM competencia c
        INNER JOIN cursocompetencia cc ON cc.competenciaid = c.id
        WHERE cc.cursoid = :cursoId
        ORDER BY SUBSTRING(c.codigo, 3)
    """)
    Flux<Competencia> findByCursoId(Long cursoId);

}


