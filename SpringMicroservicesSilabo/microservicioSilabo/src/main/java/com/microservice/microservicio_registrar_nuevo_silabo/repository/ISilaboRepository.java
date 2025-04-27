package com.microservice.microservicio_registrar_nuevo_silabo.repository;

import com.microservice.microservicio_registrar_nuevo_silabo.entity.Silabo1_IF;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ISilaboRepository extends R2dbcRepository<Silabo1_IF,Long> {

}
