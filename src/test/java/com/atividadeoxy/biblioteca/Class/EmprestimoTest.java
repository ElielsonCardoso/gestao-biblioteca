package com.atividadeoxy.biblioteca.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;

public class EmprestimoTest {

    @Test
    public void testEmprestimo() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João da Silva");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Testes Java");

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(1L);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.of(2024, 7, 1));
        emprestimo.setDataDevolucao(LocalDate.of(2024, 7, 15));
        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);

        assertEquals(1L, emprestimo.getId());
        assertEquals(usuario, emprestimo.getUsuario());
        assertEquals(livro, emprestimo.getLivro());
        assertEquals(LocalDate.of(2024, 7, 1), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.of(2024, 7, 15), emprestimo.getDataDevolucao());
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimo.getStatus());
    }
}
