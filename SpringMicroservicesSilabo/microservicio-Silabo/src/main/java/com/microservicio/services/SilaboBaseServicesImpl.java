package com.microservicio.services;

import com.microservicio.entities.SilaboBase;
import com.microservicio.entities.CursoPrerrequisito;
import com.microservicio.entities.Logro;
import com.microservicio.repositories.SilaboBaseRepository;
import com.microservicio.repositories.CursoPrerrequisitoRepository;
import com.microservicio.repositories.LogroRepositorio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <-- ¡Importante!

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service  // <-- Esto hace que Spring registre el bean
public class SilaboBaseServicesImpl implements ISilaboBaseService {

    @Autowired
    private SilaboBaseRepository silaboBaseRepository;

    @Autowired
    private CursoPrerrequisitoRepository cursoPrerrequisitoRepository;

    @Autowired
    private LogroRepositorio logroRepository;

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

    @Override
    public Mono<SilaboBase> buscarPorCodigo(String codigo) {
        return silaboBaseRepository.findById(Long.parseLong(codigo));
    }

    @Override
    public Mono<SilaboBase> buscarPorNombre(String nombre) {
        return silaboBaseRepository.findByNombreDocumentoSilabo(nombre);
    }

    @Override
    public Flux<CursoPrerrequisito> obtenerPrerequisitosPorCurso(Long idCurso) {
        return cursoPrerrequisitoRepository.findCursosPreRequisitosbyIdSilabo(idCurso);
    }

    @Override
    public Mono<SilaboBase> guardarEstrategiaDidactica(Long id, String estrategiaDidactica) {
        return silaboBaseRepository.GuardarEstrategiaDidactica(id, estrategiaDidactica);
    }

    @Override
    public Mono<SilaboBase> guardarBibliografia(Long id, String bibliografia) {
        return silaboBaseRepository.GuardarBibliografia(id, bibliografia);
    }

    @Override
    public Flux<Logro> guardarVariosLogros(List<Logro> logros, Long idSilabo, Long idCompetencia) {
        return logroRepository.guardarVariosLogros(logros, idSilabo, idCompetencia);
    }

    @Override
    public Mono<Void> eliminarVariosLogros(List<Long> idsLogros) {
        return logroRepository.eliminarVariosLogros(idsLogros);
    }

    @Override
    public Mono<Long> obtenerCursoCompetencia( Long idSilabo, Long idCompetencia) {
        return silaboBaseRepository.obtenerCursoCompetencia(idSilabo, idCompetencia);
    }

}

