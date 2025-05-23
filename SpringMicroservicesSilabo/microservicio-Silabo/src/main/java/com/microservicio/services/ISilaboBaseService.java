package com.microservicio.services;

import java.util.List;

import com.microservicio.entities.CursoPrerrequisito;
import com.microservicio.entities.Logro;
import com.microservicio.entities.SilaboBase;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISilaboBaseService {

    //metodos para editar silabo

    //recuperar silabo por nombre
    Mono<SilaboBase> obtenerSilaboPorNombre(String nombreSilabo);

    // guardar Informacion General Silabo
    Mono<SilaboBase> guardarSilaboIG(Long id, String areaDeEstudio, String nombreDocente, String emailDocente);

    //guardar estrategia didactica
    Mono<SilaboBase> guardarEstrategiaDidactica(Long id, String estrategiaDidactica);

    //guardar bibliografia
    Mono<SilaboBase> guardarBibliografia(Long id, String bibliografia);

    Mono<SilaboBase> crearNuevoSilaboConNombre(String nombreDocumento, Long idCurso);

    // Métodos de búsqueda
    Mono<SilaboBase> buscarPorCodigo(String codigo);
    Mono<SilaboBase> buscarPorNombre(String nombre);

    // Métodos para obtener prerequisitos
    Flux<CursoPrerrequisito> obtenerPrerequisitosPorCurso(Long idCurso);

    // Métodos para obtener logros
    Flux<Logro> guardarVariosLogros(List<Logro> logros, Long idSilabo, Long idCompetencia);
    Mono<Void> eliminarVariosLogros(List<Long> idsLogros);

    Mono<Long> obtenerCursoCompetencia( Long idSilabo, Long idCompetencia);

}
