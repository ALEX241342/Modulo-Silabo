package com.microservicio.repositories;

import com.microservicio.entities.SilaboBase;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Repository
public interface SilaboBaseRepository extends R2dbcRepository<SilaboBase,Long> {

    Mono<Boolean> existsByNombreDocumentoSilabo(String nombreDocumentoSilabo);

    default Mono<SilaboBase> guardarSoloNombre(String nombreDocumento,Long idCurso) {
        return save(new SilaboBase(nombreDocumento,idCurso));
    }


    default Mono<SilaboBase> guardarSilaboIG(Long id,String areaEstudios,String nombreCompletoDocente, String emailDocente) {
         return findById(id)
                 .switchIfEmpty(Mono.error(
                         new ResponseStatusException(HttpStatus.NOT_FOUND, "Sílabo no encontrado")
                 ))
                 .flatMap(silabo -> {
                     //actualizacion
                     if(areaEstudios != null) silabo.setAreaEstudios(areaEstudios);
                     if(nombreCompletoDocente != null) silabo.setNombreCompletoDocente(nombreCompletoDocente);
                     if(emailDocente != null) silabo.setEmailDocente(emailDocente);
                     return save(silabo);
                 });
    }


}
