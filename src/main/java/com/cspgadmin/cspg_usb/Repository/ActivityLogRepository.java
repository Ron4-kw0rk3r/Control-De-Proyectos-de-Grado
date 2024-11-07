package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Métodos personalizados si son necesarios
    List<ActivityLog> findByUsuarioIdAndFechaRegistroBetween(Long usuarioId, LocalDateTime inicio, LocalDateTime fin);
    List<ActivityLog> findByUsuarioId(Long usuarioId);
} 