package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividadeoxy.biblioteca.Class.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
