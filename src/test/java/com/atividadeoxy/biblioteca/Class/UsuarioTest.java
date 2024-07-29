package com.atividadeoxy.biblioteca.Class;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    public void testUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Joelson Brox");

        assertEquals(1L, usuario.getId());
        assertEquals("Joelson Brox", usuario.getNome());
    }
}
