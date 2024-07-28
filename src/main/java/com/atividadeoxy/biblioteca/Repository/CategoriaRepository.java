package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividadeoxy.biblioteca.Class.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
