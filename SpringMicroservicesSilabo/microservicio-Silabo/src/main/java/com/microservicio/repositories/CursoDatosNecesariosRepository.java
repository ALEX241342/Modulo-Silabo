package com.microservicio.repositories;

import com.microservicio.entities.CursoDatosNecesarios;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoDatosNecesariosRepository extends R2dbcRepository<CursoDatosNecesarios,Long> {



}
