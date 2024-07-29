package com.atividadeoxy.biblioteca.Service;

import com.atividadeoxy.biblioteca.Class.Categoria;
import com.atividadeoxy.biblioteca.Repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        Categoria categoria1 = new Categoria(1L, "Ficção");

        Categoria categoria2 = new Categoria(2L, "Não-Ficção");

        List<Categoria> categoriasMock = Arrays.asList(categoria1, categoria2);

        when(categoriaRepository.findAll()).thenReturn(categoriasMock);

        List<Categoria> categorias = categoriaService.findAll();

        assertThat(categorias).isNotNull();
        assertThat(categorias.size()).isEqualTo(2);
        assertThat(categorias.get(0).getDescricao()).isEqualTo("Ficção");
        assertThat(categorias.get(1).getDescricao()).isEqualTo("Não-Ficção");

        verify(categoriaRepository, times(1)).findAll();
    }
}
