package com.microservicio.controller;

import com.microservicio.entities.CursoDatosNecesarios;
import com.microservicio.entities.SilaboBase;
import com.microservicio.entities.Competencia;
import com.microservicio.entities.CursoPrerrequisito;
import com.microservicio.entities.Logro;
import com.microservicio.services.IBusquedaCursoServices;
import com.microservicio.services.ICursoDatosNecesariosServices;
import com.microservicio.services.ICursoPrerrequisitoServices;
import com.microservicio.services.ISilaboBaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("silabo")
@Validated
public class SilaboController {

    @Autowired
    private ISilaboBaseService silaboBaseService;
    @Autowired
    private ICursoDatosNecesariosServices cursoService;
    @Autowired
    private ICursoPrerrequisitoServices cursoPrerrequisitoService;

    @PostMapping("/nuevo")
    public Mono<SilaboBase> crearNuevoSilabo(@RequestBody Map<String, Object> datos) {
        String nombre = datos.get("nombreDocumento").toString();
        Long idCurso = Long.valueOf(datos.get("idCurso").toString());
        return silaboBaseService.crearNuevoSilaboConNombre(nombre, idCurso);
    }

    //corregir, esto debe guardar silabo,modalidad-curso y prerrequisito
    @PutMapping("/modificar/{id}")
    Mono<SilaboBase> guardarDatosSilaboIG(@PathVariable Long idSilabo,@RequestBody @Valid Map<String, Object> silaboActualizado){
        return silaboBaseService.guardarSilaboIG(idSilabo,
                (String) silaboActualizado.get("modalidad"),
                (String) silaboActualizado.get("nombreCompletoDocente"),
                (String) silaboActualizado.get("emailDocente"));
    }

    @GetMapping("/{idSilabo}")
    public Mono<SilaboBase> obtenerSilaboPorId(@PathVariable Long idSilabo) {
        return silaboBaseService.buscarPorCodigo(idSilabo.toString());
    }
    @GetMapping("/{idSilabo}/curso")
    public Mono<CursoDatosNecesarios> obtenerCursoPorSilabo(@PathVariable Long idSilabo) {
        return silaboBaseService.buscarPorCodigo(idSilabo.toString())
            .flatMap(silabo -> cursoService.encontrarCursoPorId(silabo.getIdCurso()));
    }

    @GetMapping("/{idSilabo}/competencias")
    public Flux<Competencia> obtenerCompetenciasPorCurso(@PathVariable Long idSilabo) {
        return silaboBaseService.buscarPorCodigo(idSilabo.toString())
            .flatMap(silabo -> cursoService.encontrarCursoPorId(silabo.getIdCurso()))
            .flatMapMany(curso -> cursoService.obtenerCompetenciasPorCurso(curso.getIdCurso()))
            .onErrorResume(e -> {
                e.printStackTrace();
                return Flux.empty();
            });
    }

    @GetMapping("/{idSilabo}/prerequisitos")
    public Flux<CursoPrerrequisito> obtenerPrerequisitosPorCurso(@PathVariable Long idSilabo) {
        return silaboBaseService.buscarPorCodigo(idSilabo.toString())
            .flatMapMany(silabo -> silaboBaseService.obtenerPrerequisitosPorCurso(silabo.getIdSilabo()));
    }

    @PutMapping("/{idSilabo}/estrategiaDidactica")
    public Mono<SilaboBase> guardarEstrategiaDidactica(@PathVariable Long idSilabo, @RequestBody String estrategiaDidactica) {
        return silaboBaseService.guardarEstrategiaDidactica(idSilabo, estrategiaDidactica);
    }

    @PutMapping("/{idSilabo}/bibliografia")
    public Mono<SilaboBase> guardarBibliografia(@PathVariable Long idSilabo, @RequestBody String bibliografia) {
        return silaboBaseService.guardarBibliografia(idSilabo, bibliografia);
    }

    @PutMapping("/{idSilabo}/IG")
    public Mono<SilaboBase> guardarInformacionGeneral(@PathVariable Long idSilabo, @RequestBody @Valid Map<String, String> datos) {
        return silaboBaseService.guardarSilaboIG(idSilabo, datos.get("areaDeEstudio"), datos.get("nombreDocente"), datos.get("emailDocente"));
    }
    
    @PostMapping("/{idSilabo}/nuevoPrerrequisito")
    public Flux<CursoPrerrequisito> guardarNuevoPrerrequisitos(
        @PathVariable Long idSilabo,
        @RequestBody Map<String, List<CursoPrerrequisito>> request
    ) {
        List<CursoPrerrequisito> prerrequisitos = request.get("lista");
        return cursoPrerrequisitoService.guardarVariosPrerrequisitos(idSilabo, prerrequisitos);
    }

    @PostMapping("/{idSilabo}/borrarPrerrequisito")
    public Mono<Void> borrarPrerrequisito(@PathVariable Long idSilabo, @RequestBody List<Long> datos) {
        return cursoPrerrequisitoService.eliminarVariosPrerrequisitos(datos);
    }

    @PostMapping("/{idSilabo}/competencias/{idCompetencia}/nuevoLogro")
    public Flux<Logro> guardarNuevoLogro(
        @PathVariable Long idSilabo, 
        @PathVariable Long idCompetencia, 
        @RequestBody @Valid Map<String, List<String>> request
    ) {
        List<String> descripciones = request.get("lista");
        List<Logro> logros = new ArrayList<>();
        for (String descripcion : descripciones) {
            Logro logro = new Logro();
            logro.setDescripcionLogro(descripcion);
            logros.add(logro);
        }
    
        return silaboBaseService.obtenerCursoCompetencia(idSilabo, idCompetencia)
        .flatMapMany(idCursoCompetencia -> silaboBaseService.guardarVariosLogros(logros, idSilabo, idCursoCompetencia));
    }

    @PostMapping("/{idSilabo}/borrarLogro")
    public Mono<Void> borrarLogro(@PathVariable Long idSilabo, @RequestBody List<Long> datos) {
        return silaboBaseService.eliminarVariosLogros(datos);
    }
}