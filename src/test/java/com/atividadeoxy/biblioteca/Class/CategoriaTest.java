package com.atividadeoxy.biblioteca.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CategoriaTest {

    @Test
    public void testCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setDescricao("Ficção");

        assertEquals(1L, categoria.getId());
        assertEquals("Ficção", categoria.getDescricao());
    }
}
