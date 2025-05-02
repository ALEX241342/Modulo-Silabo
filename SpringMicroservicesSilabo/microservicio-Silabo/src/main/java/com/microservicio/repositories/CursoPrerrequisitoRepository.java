package com.microservicio.repositories;

import com.microservicio.entities.CursoPrerrequisito;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CursoPrerrequisitoRepository extends R2dbcRepository<CursoPrerrequisito,Long> {

    @Query("SELECT * FROM cursoprerrequisito WHERE id_silabo = :idSilabo")
    Flux<CursoPrerrequisito> findCursosPreRequisitosbyIdSilabo(Long idSilabo);

}
