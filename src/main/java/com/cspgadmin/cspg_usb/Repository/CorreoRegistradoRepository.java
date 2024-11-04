
package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.CorreoRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorreoRegistradoRepository extends JpaRepository<CorreoRegistrado, Long> {
}