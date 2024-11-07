package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Etapa;
import com.cspgadmin.cspg_usb.Model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    List<Etapa> findByProyecto(Proyecto proyecto);
    List<Etapa> findByProyectoAndCompletado(Proyecto proyecto, boolean completado);
    
    @Query("SELECT COUNT(e) FROM Etapa e WHERE e.proyecto = :proyecto AND e.completado = true")
    Long countCompletadasByProyecto(@Param("proyecto") Proyecto proyecto);
} 