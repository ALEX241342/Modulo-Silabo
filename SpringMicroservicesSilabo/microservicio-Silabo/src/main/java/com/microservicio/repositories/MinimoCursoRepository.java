package com.microservicio.repositories;

import com.microservicio.entities.MinimoCurso;
import com.microservicio.entities.MinimoSilabo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MinimoCursoRepository extends R2dbcRepository<MinimoCurso,Long> {








}
