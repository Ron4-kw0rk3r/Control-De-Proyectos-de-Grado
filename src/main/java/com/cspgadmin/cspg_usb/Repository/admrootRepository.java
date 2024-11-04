package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.adminroot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface admrootRepository extends JpaRepository<adminroot, String> {

}

