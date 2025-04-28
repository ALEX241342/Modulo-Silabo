package com.microservicio.repositories;

import com.microservicio.dtos.DTODetallesBusquedaCurso;
import com.microservicio.dtos.DTOMinDatosCurso;
import com.microservicio.entities.MinimoCurso;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
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
    Mono<DTODetallesBusquedaCurso> buscarPorCodigoCurso(String codigo);

    //al momento que se le muestre la lista el usuario
    //debera escoger una opcion de la lista, al darle
    //click seleccionara el id de ese curso
    //ese id lo enviara al back para poder autocompletar los otros campos
    //para confirmar si se trata del mismo
    @Query("""
            SELECT
            C.id AS id,
            C.codigo AS codigoCurso,
            C.nombre AS nombreCurso,
            PE.descripcion AS planDeEstudios
            FROM curso C
            JOIN planestudios PE ON C.planestudiosid = PE.id
            WHERE
            LOWER(C.nombre) LIKE LOWER(CONCAT('%', :parteNombreCurso, '%'))
            LIMIT 5
            """)
    Flux<DTOMinDatosCurso> listarOpcionesPorNombreCurso(String parteNombreCurso);


}
