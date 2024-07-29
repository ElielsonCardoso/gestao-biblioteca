package com.atividadeoxy.biblioteca.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LivroTest {

    @Test
    public void testLivro() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setDescricao("Tecnologia");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setIsbn("9780132350884");
        livro.setDataPublicacao(LocalDate.of(2008, 8, 1));
        livro.setCategoria(categoria);

        assertEquals(1L, livro.getId());
        assertEquals("Clean Code", livro.getTitulo());
        assertEquals("Robert C. Martin", livro.getAutor());
        assertEquals("9780132350884", livro.getIsbn());
        assertEquals(LocalDate.of(2008, 8, 1), livro.getDataPublicacao());
        assertEquals(categoria, livro.getCategoria());
    }
}
