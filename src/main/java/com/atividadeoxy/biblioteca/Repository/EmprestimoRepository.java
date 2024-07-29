package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atividadeoxy.biblioteca.Class.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>, EmprestimoRepositoryCustom {
}
