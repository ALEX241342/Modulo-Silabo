package com.silabo.repositorio;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.silabo.entidades.Silabo;

@Repository
public interface SilaboCustomRepository extends JpaRepository<Silabo, Integer> {

        
        @Query(value = """
            SELECT 
                c.nombre AS nombre,
                c.codigo AS codigo,
                c.tipo AS tipo,
                c.numcreditos AS numCreditos,
                c.numhorasteoria AS numHorasTeoria,
                c.numhoraspractica AS numHorasPractica,
                c.ciclo AS ciclo,
                c.modalidad AS modalidad,
                c.sumilla AS sumilla,
                pe.descripcion AS planEstudios,
                pa.descripcion AS periodoAcademico,
                d.nombre AS DEPARTAMENTO,
                ds.nombre AS SUPERDEPARTAMENTO,
                i.nombreCorto AS institucion,
                s.area_estudios AS areaEstudios,
                s.estrategia_didactica AS estrategia,
                s.bibliografia AS bibliografia,
                s.nombre_completo_profesor AS nombreProfesor,
                s.email_profesor AS emailProfesor
            FROM silabo s
            JOIN curso c ON s.cursoid = c.id
            JOIN periodoacademico pa ON c.periodoacademicoid = pa.id
            JOIN planestudios pe ON c.planestudiosid = pe.id
            JOIN departamento d ON c.departamentoid = d.id
            JOIN institucion i ON c.institucionid = i.id
            JOIN departamento ds ON d.departamentoid = ds.id
            WHERE s.id_silabo = :idSilabo
            """, nativeQuery = true)
        Map<String, Object> obtenerSilaboBase(@Param("idSilabo") Integer idSilabo);
    
        @Query(value = """
            SELECT codigo_curso, nombre_curso 
            FROM cursoprerrequisito 
            WHERE id_silabo = :idSilabo
            """, nativeQuery = true)
        List<Map<String, String>> obtenerPrerrequisitos(@Param("idSilabo") Integer idSilabo);

        @Query(value = """
            SELECT  co.codigo , co.tipo, co.descripcion
            FROM competencia co
            JOIN cursocompetencia cc ON cc.competenciaid = co.id
            JOIN curso c ON c.id = cc.cursoid
            JOIN silabo s ON s.cursoid = c.id
            WHERE s.id_silabo = :idSilabo
            """, nativeQuery = true)
        List<Map<String, Object>> obtenerCompetencias(@Param("idSilabo") Integer idSilabo);

        @Query(value = """
            SELECT l.codigo_logro, l.descripcion_logro, co.codigo AS pre
            FROM logro l
            JOIN cursocompetencia cc ON l.id_competencia = cc.id
            JOIN competencia co ON co.id = cc.competenciaid
            JOIN curso c ON c.id = cc.cursoid
            JOIN silabo s ON s.cursoid = c.id
            WHERE s.id_silabo = :idSilabo
            """, nativeQuery = true)
        List<Map<String, Object>> obtenerLogros(@Param("idSilabo") Integer idSilabo);
    
}
