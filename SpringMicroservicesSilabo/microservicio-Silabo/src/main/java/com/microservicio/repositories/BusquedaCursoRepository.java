package com.microservicio.repositories;

import com.microservicio.dtos.DTOBusquedaDatosCurso;
import com.microservicio.dtos.DTODatosNombreCurso;
import com.microservicio.entities.MinimoCurso;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BusquedaCursoRepository extends R2dbcRepository<MinimoCurso,Long> {

    @Query("""
            SELECT codigo AS codigoCurso,
            nombre AS nombreCurso,
            numero_periodo as numeroPeriodo,
            año as anio,
            ciclo,
            creditos as numeroDeCreditos,
            plan_estudios as planDeEstudios,
            tipo as tipoDeCurso
            FROM vw_busqueda_de_curso
            WHERE LOWER(codigo) = LOWER(:codigo)
            """)
    Mono<DTOBusquedaDatosCurso> buscarPorCodigoCurso(String codigo);

    //al momento que se le muestre la lista el usuario
    //debera escoger una opcion de la lista, al darle
    //click seleccionara el id de ese curso
    //ese id lo enviara al back para poder autocompletar los otros campos
    //para confirmar si se trata del mismo

    @Query("""
            SELECT
            id,
            codigo AS codigoCurso,
            nombre AS nombreCurso,
            plan_estudios as planDeEstudios
            FROM vw_busqueda_de_curso
            WHERE
            LOWER(nombre) LIKE LOWER(CONCAT(:parteNombreCurso, '%'))
            LIMIT 5
            """)
    Flux<DTODatosNombreCurso> listarOpcionesPorNombreCurso(String parteNombreCurso);

    @Query("SELECT codigo FROM curso WHERE id = :id")
    Mono<String> buscarCodigoPorNombre(Long id);

}
