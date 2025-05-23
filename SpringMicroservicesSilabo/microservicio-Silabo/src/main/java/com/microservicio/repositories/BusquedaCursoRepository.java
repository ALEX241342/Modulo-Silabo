package com.microservicio.repositories;
import com.microservicio.entities.CursoDatosNecesarios;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BusquedaCursoRepository extends R2dbcRepository<CursoDatosNecesarios,Long> {
    
    @Query("SELECT * FROM vw_busqueda_de_curso WHERE codigo = :codigo LIMIT 1")
    Mono<CursoDatosNecesarios> debugRawData(String codigo);

    @Query("""
            SELECT id,
            codigo,
            nombre,
            numero_periodo,
            año,
            ciclo,
            creditos,
            plan_estudios,
            tipo
            FROM vw_busqueda_de_curso
            WHERE LOWER(codigo) = LOWER(:codigo)
            LIMIT 1
            """)
    Mono<CursoDatosNecesarios> buscarPorCodigoCurso(String codigo);

    //al momento que se le muestre la lista el usuario
    //debera escoger una opcion de la lista, al darle
    //click seleccionara el id de ese curso
    //ese id lo enviara al back para poder autocompletar los otros campos
    //para confirmar si se trata del mismo

    @Query("""
            SELECT
            id,
            codigo,
            nombre,
            plan_estudios
            FROM vw_busqueda_de_curso
            WHERE
            LOWER(nombre) LIKE LOWER(CONCAT(:parteNombreCurso, '%'))
            """)
    Flux<CursoDatosNecesarios> listarOpcionesPorNombreCurso(String parteNombreCurso);


    // cuando seleccione el nombre de un curso, se enviara el id correspondiente al nombre de escogido
    @Query("SELECT codigo FROM curso WHERE id = :id")
    Mono<String> buscarCodigoPorNombre(Long id);

}
