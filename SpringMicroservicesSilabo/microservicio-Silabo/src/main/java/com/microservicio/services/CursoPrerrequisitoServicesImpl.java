package com.microservicio.services;

import com.microservicio.entities.CursoPrerrequisito;
import com.microservicio.repositories.CursoPrerrequisitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CursoPrerrequisitoServicesImpl implements ICursoPrerrequisitoServices{

    @Autowired
    private CursoPrerrequisitoRepository cursoPrerrequisitoRepository;

    @Override
    public Mono<CursoPrerrequisito> guardarCursoPreRequisito(CursoPrerrequisito cursoPrerrequisito) {
        return cursoPrerrequisitoRepository.save(cursoPrerrequisito);
    }

    @Override
    public Flux<CursoPrerrequisito> recuperarCursosPreRequisito(Long idSilabo) {
        return cursoPrerrequisitoRepository.findCursosPreRequisitosbyIdSilabo(idSilabo);
    }
}
