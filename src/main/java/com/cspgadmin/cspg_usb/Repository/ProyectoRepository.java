package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Proyecto;
import com.cspgadmin.cspg_usb.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    List<Proyecto> findByEstudiante(Usuario estudiante);
    List<Proyecto> findByDocente(Usuario docente);
    List<Proyecto> findByEstado(String estado);
    
    @Query("SELECT p FROM Proyecto p WHERE p.estudiante.carrera = :carrera")
    List<Proyecto> findByCarrera(@Param("carrera") String carrera);
    
    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estado = :estado")
    Long countByEstado(@Param("estado") String estado);
} 