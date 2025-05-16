package com.silabo.formato;
import com.silabo.entidades.Silabo;
import com.silabo.repositorio.SilaboRepository;
import com.silabo.repositorio.SilaboCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class DocumentoService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentoService.class);

    @Autowired
    private SilaboRepository silaboRepository;

    @Autowired
    private SilaboCustomRepository silaboCustomRepository;

    public Map<String, Object> datosDesdeBD(int IdSilabo) {
        try {            
            Silabo silabo = silaboRepository.findByIdSilabo(IdSilabo)
                    .orElseThrow(() -> new RuntimeException("Silabo no encontrado"));

            Map<String, Object> datosOriginal = silaboCustomRepository.obtenerSilaboBase(IdSilabo);
            if (datosOriginal == null || datosOriginal.isEmpty()) {
                throw new RuntimeException("No se encontraron datos base del silabo");
            }
            logger.info("Datos base obtenidos correctamente: {}", datosOriginal);

            Map<String, Object> datos = new HashMap<>(datosOriginal);
            datos.put("silabo", silabo.getNombreDocumentoSilabo());

            // Obtener competencias
            List<Map<String, Object>> competencias = silaboCustomRepository.obtenerCompetencias(IdSilabo);
            logger.info("Competencias encontradas: {}", competencias.size());
            
            if (competencias != null && !competencias.isEmpty()) {
                List<String> codigosCompetencias = competencias.stream()
                        .map(comp -> (String) comp.get("codigo"))
                        .collect(Collectors.toList());
                List<String> tipoCompetencias = competencias.stream()
                        .map(comp -> (String) comp.get("tipo"))
                        .collect(Collectors.toList());
                List<String> descripcionesCompetencias = competencias.stream()
                        .map(comp -> (String) comp.get("descripcion"))
                        .collect(Collectors.toList());


                datos.put("codCompetencias", codigosCompetencias);
                datos.put("tipCompetencias", tipoCompetencias);
                datos.put("desCompetencias", descripcionesCompetencias);
            } else {
                logger.warn("No se encontraron competencias para el silabo ID: {}", IdSilabo);
                datos.put("codCompetencias", List.of());
                datos.put("tipCompetencias", List.of());
                datos.put("desCompetencias", List.of());
            }

            // Obtener prerrequisitos
            List<Map<String, String>> prerrequisitos = silaboCustomRepository.obtenerPrerrequisitos(IdSilabo);
            
            if (prerrequisitos != null && !prerrequisitos.isEmpty()) {
                List<String> combinacion = prerrequisitos.stream()
                        .map(prer -> prer.get("codigo_curso") + " - " + prer.get("nombre_curso"))
                        .collect(Collectors.toList());
                datos.put("prerrequisitos", combinacion);
            } else {
                datos.put("prerrequisitos", List.of());
            }

            // Obtener logros
            List<Map<String, Object>> logros = silaboCustomRepository.obtenerLogros(IdSilabo);
            
            if (logros != null && !logros.isEmpty()) {
                List<String> codLogro = new ArrayList<>();
                List<String> desLogro = new ArrayList<>();
                
                for (Map<String, Object> logro : logros) {
                    String pre = (String) logro.get("pre");
                    String codigo = (String) logro.get("codigo_logro");
                    String descripcion = (String) logro.get("descripcion_logro");
                    
                    codLogro.add(pre + " - " + codigo);
                    desLogro.add(descripcion);
                }

                datos.put("codLogro", codLogro);
                datos.put("desLogro", desLogro);
            } else {
                datos.put("codLogro", List.of());
                datos.put("desLogro", List.of());
            }

            logger.info("Procesamiento de datos completado exitosamente. Datos finales: {}", datos);
            return datos;
        } catch (Exception e) {
            logger.error("Error al procesar datos del silabo ID: " + IdSilabo, e);
            throw new RuntimeException("Error al procesar datos del silabo: " + e.getMessage(), e);
        }
    }
}
