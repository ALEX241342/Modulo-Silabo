package com.microservicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.entities.Competencia;
import com.microservicio.entities.Logro;
import com.microservicio.services.ICursoDatosNecesariosServices;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/curso")
public class CompetenciaController {

    @Autowired
    private ICursoDatosNecesariosServices cursoService;

    @GetMapping("/logros/{idCurso}/{idCompetencia}")
    public Flux<Logro> obtenerLogrosPorCursoYCompetencia(@PathVariable Long idCurso, @PathVariable Long idCompetencia) {
        return cursoService.obtenerLogrosPorCursoYCompetencia(idCurso, idCompetencia);
    }


}



