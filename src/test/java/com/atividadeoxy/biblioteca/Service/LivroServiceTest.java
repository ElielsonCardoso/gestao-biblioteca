package com.atividadeoxy.biblioteca.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.atividadeoxy.biblioteca.Class.Categoria;
import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Repository.LivroRepository;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    private Categoria mockCategoria;

    private Livro mockLivro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCategoria = new Categoria(
                1L,
                "Tecnologia");

        mockLivro = new Livro(
                1L,
                "Introdução à Linguagem SQL",
                "Thomas Nield",
                "9788575225011",
                LocalDate.now().minusYears(10),
                mockCategoria);
    }

    @Test
    void testCreateLivro() {
        when(livroRepository.save(any(Livro.class))).thenReturn(mockLivro);

        Livro livro = livroService.createLivro(mockLivro);

        assertThat(livro.getId()).isEqualTo(1L);
        assertThat(livro.getTitulo()).isEqualTo("Introdução à Linguagem SQL");
        assertThat(livro.getAutor()).isEqualTo("Thomas Nield");
        assertThat(livro.getIsbn()).isEqualTo("9788575225011");
        assertThat(livro.getDataPublicacao()).isEqualTo(LocalDate.now().minusYears(10));
        assertThat(livro.getCategoria().getDescricao()).isEqualTo("Tecnologia");
    }

    @Test
    void testFindAll() {
        Page<Livro> mockPage = new PageImpl<>(List.of(mockLivro));

        when(livroRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Livro> page = livroService.findAll(PageRequest.of(0, 10));

        assertEquals(1, page.getContent().size());
        assertEquals(1L, page.getContent().get(0).getId());
    }

    @Test
    void testFindById() {
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(mockLivro));

        Optional<Livro> livro = livroService.findById(1L);

        assertTrue(livro.isPresent());
        assertEquals(1L, livro.get().getId());
    }

    @Test
    void testDeleteLivro() {
        doNothing().when(livroRepository).deleteById(1L);

        assertDoesNotThrow(() -> livroService.deleteLivro(1L));
    }

    @Test
    void testFindRecomendacaoLivros() {
        LivroDTO mockLivroDTO = new LivroDTO();
        mockLivroDTO.setId(1L);
        mockLivroDTO.setTitulo("Introdução à Linguagem SQL");
        mockLivroDTO.setAutor("Thomas Nield");
        mockLivroDTO.setIsbn("9788575225011");
        mockLivroDTO.setDataPublicacao(LocalDate.now().minusYears(10));
        mockLivroDTO.setDescricaoCategoria("Tecnologia");
        mockLivroDTO.setEmprestado(false);

        Page<LivroDTO> mockPage = new PageImpl<>(List.of(mockLivroDTO));

        when(livroRepository.findLivrosRecomendados(any(Pageable.class), anyLong())).thenReturn(mockPage);

        Page<LivroDTO> page = livroService.findLivrosRecomendados(PageRequest.of(0, 10), 1L);

        assertNotNull(page);
        assertEquals(1, page.getContent().size());

        LivroDTO livroDTO = page.getContent().get(0);
        assertThat(livroDTO.getId()).isEqualTo(1L);
        assertThat(livroDTO.getTitulo()).isEqualTo("Introdução à Linguagem SQL");
        assertThat(livroDTO.getAutor()).isEqualTo("Thomas Nield");
        assertThat(livroDTO.getIsbn()).isEqualTo("9788575225011");
        assertThat(livroDTO.getDataPublicacao()).isEqualTo(LocalDate.now().minusYears(10));
        assertThat(livroDTO.getDescricaoCategoria()).isEqualTo("Tecnologia");
        assertThat(livroDTO.getEmprestado()).isEqualTo(false);
    }

    @Test
    void testFindLivroByParam() {
        LivroDTO mockLivroDTO = new LivroDTO();
        mockLivroDTO.setId(1L);
        mockLivroDTO.setTitulo("Introdução à Linguagem SQL");
        mockLivroDTO.setAutor("Thomas Nield");
        mockLivroDTO.setIsbn("9788575225011");
        mockLivroDTO.setDataPublicacao(LocalDate.now().minusYears(10));
        mockLivroDTO.setDescricaoCategoria("Tecnologia");
        mockLivroDTO.setEmprestado(false);

        Page<LivroDTO> mockPage = new PageImpl<>(List.of(mockLivroDTO));

        when(livroRepository.findLivroByParam(any(Pageable.class), any(LivroParam.class))).thenReturn(mockPage);

        LivroParam livroParam = new LivroParam();
        livroParam.setTitulo("sql");

        Page<LivroDTO> page = livroService.findLivroByParam(PageRequest.of(0, 10), livroParam);

        assertNotNull(page);
        assertEquals(1, page.getContent().size());

        LivroDTO livroDTO = page.getContent().get(0);
        assertThat(livroDTO.getId()).isEqualTo(1L);
        assertThat(livroDTO.getTitulo()).isEqualTo("Introdução à Linguagem SQL");
        assertThat(livroDTO.getAutor()).isEqualTo("Thomas Nield");
        assertThat(livroDTO.getIsbn()).isEqualTo("9788575225011");
        assertThat(livroDTO.getDataPublicacao()).isEqualTo(LocalDate.now().minusYears(10));
        assertThat(livroDTO.getDescricaoCategoria()).isEqualTo("Tecnologia");
        assertThat(livroDTO.getEmprestado()).isEqualTo(false);
    }
}
