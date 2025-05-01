package com.microservicio.repositories;

import com.microservicio.entities.MinimoSilabo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinimoSilaboRepository extends R2dbcRepository<MinimoSilabo,Long> {

}
