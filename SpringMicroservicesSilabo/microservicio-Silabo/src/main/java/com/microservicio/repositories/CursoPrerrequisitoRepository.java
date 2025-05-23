package com.microservicio.repositories;

import com.microservicio.entities.CursoPrerrequisito;

import java.util.List;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CursoPrerrequisitoRepository extends R2dbcRepository<CursoPrerrequisito,Long> {

    @Query("SELECT * FROM cursoprerrequisito WHERE id_silabo = :idSilabo")
    Flux<CursoPrerrequisito> findCursosPreRequisitosbyIdSilabo(Long idSilabo);

    default Mono<CursoPrerrequisito> guardarCursoPrerrequisito(Long idSilabo, String codigoCurso, String nombreCurso) {
        return save(new CursoPrerrequisito(idSilabo, codigoCurso, nombreCurso));
    }

    default Mono<Void> eliminarPorId(Long idPrerrequisito) {
        return deleteById(idPrerrequisito);
    }


    default Flux<CursoPrerrequisito> guardarVariosPrerrequisitos(Long idSilabo, List<CursoPrerrequisito> prerrequisitos) {
    prerrequisitos.forEach(p -> p.setIdSilabo(idSilabo));
    return saveAll(prerrequisitos);
    }

    default Mono<Void> eliminarVariosPrerrequisitos(List<Long> idsPrerrequisitos) {
        return deleteAllById(idsPrerrequisitos);
    }
    
}
