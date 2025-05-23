package com.microservicio.controller;

import com.microservicio.entities.SilaboBase;
import com.microservicio.services.ISilaboBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/silabo")
@RequiredArgsConstructor
public class BusquedaSilaboController {

    @Autowired
    private ISilaboBaseService silaboBaseService;

    /**
     * Busca un silabo por su código/ID
     * @param codigo El código/ID del silabo a buscar
     * @return Mono con el silabo encontrado o error si no existe
     */
    @GetMapping("/buscar/codigo/{codigo}")
    public Mono<ResponseEntity<SilaboBase>> buscarPorCodigo(@PathVariable String codigo) {
        return silaboBaseService.buscarPorCodigo(codigo)
                .map(silabo -> ResponseEntity.ok(silabo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Busca un silabo por su nombre
     * @param nombre El nombre del silabo a buscar
     * @return Mono con el silabo encontrado o error si no existe
     */
    @GetMapping("/buscar/nombre/{nombre}")
    public Mono<ResponseEntity<SilaboBase>> buscarPorNombre(@PathVariable String nombre) {
        return silaboBaseService.buscarPorNombre(nombre)
                .map(silabo -> ResponseEntity.ok(silabo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 