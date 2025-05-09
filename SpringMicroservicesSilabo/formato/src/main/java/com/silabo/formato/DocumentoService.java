package com.silabo.formato;
import com.silabo.entidades.Silabo;
import com.silabo.entidades.Curso;
import com.silabo.repositorio.SilaboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentoService {

    @Autowired
    private SilaboRepository silaboRepository;



    public Map<String, Object> datosDesdeBD(int IdSilabo) {
        Silabo silabo = silaboRepository.findByIdSilabo(IdSilabo)
                .orElseThrow(() -> new RuntimeException("Silabo no encontrado"));

        Map<String, Object> datos = new HashMap<>();

        //Datos de silabo:
        datos.put("idSilabo", silabo.getIdSilabo());
        datos.put("nombreDocumentoSilabo", silabo.getNombreDocumentoSilabo());
        datos.put("areaEstudios", silabo.getAreaEstudios());
        datos.put("descripcionSilabo", silabo.getDescripcionSilabo());
        datos.put("estrategiaDidactica", silabo.getEstrategiaDidactica());
        datos.put("bibliografia", silabo.getBibliografia());
        datos.put("fechaCreacion", silabo.getFechaCreacion());
        datos.put("fechaUltimaModificacion", silabo.getFechaUModificacion());
        datos.put("cantidadUnidades", silabo.getCantidadUnidades());
        datos.put("nombreCompletoProfesor", silabo.getNombreCompletoProfesor());
        datos.put("emailProfesor", silabo.getEmailProfesor());    

        Curso curso = silabo.getCurso(); 
        //Datos de Curso:
        datos.put("codigo", curso.getCodigo());
        datos.put("nombre", curso.getNombre());
        datos.put("tipo", curso.getTipo());
        datos.put("numHorasTeoria", curso.getNumHorasTeoria());
        datos.put("numHorasPractica", curso.getNumHorasPractica());
        datos.put("numHorasLaboratorio", curso.getNumHorasLaboratorio());
        datos.put("numHorasCampo", curso.getNumHorasCampo());
        datos.put("numCreditos", curso.getNumCreditos());
        datos.put("ciclo", curso.getCiclo());
        datos.put("estado", curso.getEstado());
        datos.put("periodoAcademicoId", curso.getPeriodoacademicoid());
        datos.put("planEstudiosId", curso.getPlanestudiosid());
        datos.put("institucionId", curso.getInstitucionid());
        datos.put("departamentoId", curso.getDepartamentoid());
        datos.put("sumilla", curso.getSumilla());
        datos.put("modalidad", curso.getModalidad());
        datos.put("etiquetas", curso.getEtiquetas());
        //datos.put("competencias", curso.getCompetencias());

        return datos;
    }
}
