package com.microservicio.repositories;

import com.microservicio.dtos.DTODetallesBusquedaCurso;
import com.microservicio.entities.MinimoCurso;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MinimoCursoRepository extends R2dbcRepository<MinimoCurso,Long> {

    // Buscar por código exacto (case-insensitive)
    @Query(""" 
            SELECT
            C.id AS id, 
            C.codigo AS codigoCurso,
            C.nombre AS nombreCurso,
            substring_index(PA.codigo,'-',1) AS anio,
            substring_index(PA.codigo,'-',-1) AS numeroPeriodo,
            C.ciclo AS ciclo,
            C.numCreditos AS numeroDeCreditos,
            PE.descripcion AS planDeEstudios,
            C.tipo AS tipoDeCurso
            FROM curso C 
            JOIN planestudios PE ON C.planestudiosid = PE.id
            JOIN periodoacademico PA ON C.periodoacademicoid = PA.id
            WHERE UPPER(C.codigo) = UPPER(:codigo)
            """)
    Mono<DTODetallesBusquedaCurso> buscarPorCodigo(String codigo);

}
