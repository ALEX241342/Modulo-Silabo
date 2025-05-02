package com.microservicio.controllers;

import com.microservicio.dtos.DTOActualizarSilaboIG;
import com.microservicio.dtos.DTONuevoSilaboNombre;
import com.microservicio.entities.SilaboBase;
import com.microservicio.services.ISilaboBaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/silabo")
@Validated
public class SilaboController {

    @Autowired
    private ISilaboBaseService silaboBaseService;

    @PostMapping("/nuevo")
    Mono<SilaboBase> crearNuevoSilabo (@RequestBody @Valid DTONuevoSilaboNombre nuevoSilabo){
        return silaboBaseService.crearNuevoSilaboConNombre(nuevoSilabo.nombreDocumento(), nuevoSilabo.idCurso());
    }

    //corregir, esto debe guardar silabo,modalidad-curso y prerrequisito
    @PutMapping("/modificar/{id}")
    Mono<SilaboBase> guardarDatosSilaboIG(@PathVariable Long idSilabo,@RequestBody @Valid DTOActualizarSilaboIG silaboActualizado){
        return silaboBaseService.guardarSilaboIG(idSilabo,
                silaboActualizado.modalidad(),
                silaboActualizado.nombreCompletoDocente(),
                silaboActualizado.emailDocente());
    }

}
