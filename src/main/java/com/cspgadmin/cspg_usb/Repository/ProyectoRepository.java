package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Proyecto;
import com.cspgadmin.cspg_usb.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.param.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByDocente(Usuario docente);
    List<Proyecto> findByDocenteAndEstado(Usuario docente, String estado);
    Long countByEstado(String estado);
    List<Proyecto> findByDocenteId(Long docenteId);
    @Query("SELECT p FROM Proyecto p WHERE p.docente.id = :docenteId AND p.estado = :estado")
    List<Proyecto> findByDocenteIdAndEstado(@Param("docenteId") Long docenteId, @Param("estado") String estado);
    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.docente.id = :docenteId GROUP BY p.estado")
    Map<String, Long> countProyectosByEstadoAndDocenteId(@Param("docenteId") Long docenteId);
    @Query("SELECT AVG(p.progreso) FROM Proyecto p WHERE p.docente.id = ?1")
    Double getProgresoPromedioByDocenteId(Long docenteId);
    @Query("SELECT p FROM Proyecto p WHERE p.docente.id = ?1 ORDER BY p.ultimaActividad DESC")
    List<Proyecto> findRecentProyectosByDocenteId(Long docenteId);
} 