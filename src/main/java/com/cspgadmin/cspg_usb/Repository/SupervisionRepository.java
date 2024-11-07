package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Proyecto;
import com.cspgadmin.cspg_usb.Model.Supervision;
import com.cspgadmin.cspg_usb.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SupervisionRepository extends JpaRepository<Supervision, Long> {
    List<Supervision> findByProyecto(Proyecto proyecto);
    List<Supervision> findByDocente(Usuario docente);
    
    @Query("SELECT s FROM Supervision s WHERE s.supervisionDate BETWEEN :inicio AND :fin")
    List<Supervision> findBetweenDates(
        @Param("inicio") LocalDateTime inicio, 
        @Param("fin") LocalDateTime fin
    );
} 