package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Métodos personalizados si son necesarios
} 