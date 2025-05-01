package com.microservicio.controllers;

import com.microservicio.dtos.DTOBusquedaDatosCurso;
import com.microservicio.dtos.DTODatosNombreCurso;
import com.microservicio.services.IBusquedaCursoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/busqueda-curso")
public class BusquedaCursoController {

    @Autowired
    private IBusquedaCursoServices cursoService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{codigo}")
    public Mono<DTOBusquedaDatosCurso> encontrarCursoPorCodigo(@PathVariable String codigo){
        return cursoService
                .buscarPorCodigoCurso(codigo)
                .switchIfEmpty(Mono
                        .error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado")
                ));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Flux<DTODatosNombreCurso> listarCursosPorParteDelNombre(@RequestParam (name = "nombre",required = true) String parteNombre){
        return cursoService.buscarPorNombreCursos(parteNombre);
    }

    // el nombre sera escogido de la lista de cursos pero cuando se envie
    //como solicitud llegara como id
    @GetMapping("/{id}")
    Mono<DTOBusquedaDatosCurso> encontrarCursoPorNombreCursoElegido(@PathVariable Long id){
        return cursoService.buscarCodigoPorNombre(id).flatMap(
                codigo -> cursoService.buscarPorCodigoCurso(codigo)
        ).switchIfEmpty(Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado")
        ));
    }
}
