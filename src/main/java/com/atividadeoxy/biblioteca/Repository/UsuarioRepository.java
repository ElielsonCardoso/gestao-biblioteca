package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividadeoxy.biblioteca.Class.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
