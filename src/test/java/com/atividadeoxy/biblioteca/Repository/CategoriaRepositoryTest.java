package com.atividadeoxy.biblioteca.Repository;

import com.atividadeoxy.biblioteca.Class.Categoria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoriaRepositoryTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setDescricao("Ficção");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setDescricao("Não-ficção");

        List<Categoria> categoriasMock = Arrays.asList(categoria1, categoria2);

        when(categoriaRepository.findAll()).thenReturn(categoriasMock);

        List<Categoria> categorias = categoriaRepository.findAll();

        assertThat(categorias).isNotNull();
        assertThat(categorias.size()).isEqualTo(2);
        assertThat(categorias.get(0).getDescricao()).isEqualTo("Ficção");
        assertThat(categorias.get(1).getDescricao()).isEqualTo("Não-ficção");

        verify(categoriaRepository, times(1)).findAll();
    }
}
