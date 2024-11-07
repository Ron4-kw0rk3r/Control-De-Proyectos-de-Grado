package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Supervision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SupervisionRepository extends JpaRepository<Supervision, Long> {
    
    @Query("SELECT s FROM Supervision s WHERE s.docente.id = :docenteId AND s.supervisionDate BETWEEN :inicio AND :fin")
    List<Supervision> findByDocenteIdAndFechaBetween(Long docenteId, LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT COUNT(s) FROM Supervision s WHERE s.docente.id = :docenteId AND s.supervisionDate BETWEEN :inicio AND :fin")
    int countByDocenteIdAndFechaBetween(Long docenteId, LocalDateTime inicio, LocalDateTime fin);
} 