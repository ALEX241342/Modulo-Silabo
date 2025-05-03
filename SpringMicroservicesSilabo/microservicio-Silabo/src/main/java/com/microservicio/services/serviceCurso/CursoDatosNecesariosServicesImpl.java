package com.microservicio.services.serviceCurso;

import com.microservicio.entities.CursoDatosNecesarios;
import com.microservicio.repositories.CursoDatosNecesariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CursoDatosNecesariosServicesImpl implements ICursoDatosNecesariosServices {

    @Autowired
    CursoDatosNecesariosRepository cursoRepository;

    @Override
    public Mono<CursoDatosNecesarios> encontrarCursoPorId(Long idCurso) {
        return cursoRepository.findById(idCurso);
    }

    @Override
    public Mono<CursoDatosNecesarios> guardarDatosCursoIG(CursoDatosNecesarios curso){
        return encontrarCursoPorId(curso.getIdCurso())
                .flatMap(cursoRecuperado -> {
                    cursoRecuperado.setModalidad(curso.getModalidad());
                    return cursoRepository.save(cursoRecuperado);
                });
    }
}
