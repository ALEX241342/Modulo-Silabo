package com.microservicio.services;

import com.microservicio.entities.SilaboBase;
import com.microservicio.repositories.SilaboBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <-- ¡Importante!
import reactor.core.publisher.Mono;

@Service  // <-- Esto hace que Spring registre el bean
public class SilaboBaseServicesImpl implements ISilaboBaseService {

    @Autowired
    private SilaboBaseRepository silaboBaseRepository;

    @Override
    public Mono<SilaboBase> obtenerSilaboPorNombre(String nombreSilabo) {
        return null;
    }

    @Override
    public Mono<SilaboBase> guardarSilaboIG(Long id, String areaDeEstudio, String nombreDocente, String emailDocente) {
        return silaboBaseRepository.guardarSilaboIG(id, areaDeEstudio, nombreDocente, emailDocente);
    }

    @Override
    public Mono<SilaboBase> crearNuevoSilaboConNombre(String nombreDocumento, Long idCurso) {
        return silaboBaseRepository.guardarSoloNombre(nombreDocumento, idCurso);
    }
}

