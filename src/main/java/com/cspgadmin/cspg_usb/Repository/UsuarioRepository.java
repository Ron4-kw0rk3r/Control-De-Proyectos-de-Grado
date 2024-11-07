package com.cspgadmin.cspg_usb.Repository;

import com.cspgadmin.cspg_usb.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query(value = "SELECT * FROM login_usuario WHERE rol = :rol", nativeQuery = true)
    List<Usuario> findByRol(@Param("rol") String rol);
    
    @Query(value = "SELECT * FROM login_usuario WHERE email = :email AND activo = true", nativeQuery = true)
    Optional<Usuario> validateUserEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM login_usuario WHERE email = :email", nativeQuery = true)
    Optional<Usuario> findByEmail(@Param("email") String email);
} 