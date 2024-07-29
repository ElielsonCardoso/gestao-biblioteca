package com.atividadeoxy.biblioteca.Resource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.atividadeoxy.biblioteca.Class.Categoria;
import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;
import com.atividadeoxy.biblioteca.Service.LivroService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroResource.class)
public class LivroResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
                "Programação Java Básico",
                "Leandro Hossi",
                "519816531321",
                LocalDate.of(2020, 01, 01),
                mockCategoria);
    }

    @Test
    public void testCreateLivro() throws Exception {
        when(livroService.createLivro(any(Livro.class))).thenReturn(mockLivro);

        mockMvc.perform(post("/api/livros")
                        .contentType("application/json")
                        .content("{\"id\":1,\"titulo\":\"Programação Java Básico\",\"autor\":\"Leandro Hossi\",\"isbn\":\"519816531321\",\"dataPublicacao\":\"2020-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Programação Java Básico"))
                .andExpect(jsonPath("$.autor").value("Leandro Hossi"))
                .andExpect(jsonPath("$.isbn").value("519816531321"))
                .andExpect(jsonPath("$.dataPublicacao").value("2020-01-01"));
    }

    @Test
    public void testFindAll() throws Exception {
        when(livroService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(mockLivro)));

        mockMvc.perform(get("/api/livros?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].titulo").value("Programação Java Básico"))
                .andExpect(jsonPath("$.content[0].autor").value("Leandro Hossi"))
                .andExpect(jsonPath("$.content[0].isbn").value("519816531321"))
                .andExpect(jsonPath("$.content[0].dataPublicacao").value("2020-01-01"));
    }

    @Test
    public void testFindById() throws Exception {
        when(livroService.findById(1L)).thenReturn(Optional.of(mockLivro));

        mockMvc.perform(get("/api/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Programação Java Básico"))
                .andExpect(jsonPath("$.autor").value("Leandro Hossi"))
                .andExpect(jsonPath("$.isbn").value("519816531321"))
                .andExpect(jsonPath("$.dataPublicacao").value("2020-01-01"));
    }

    @Test
    public void testFindRecomendacaoLivros() throws Exception {
        LivroDTO mockLivroDTO = new LivroDTO();
        mockLivroDTO.setId(651L);
        mockLivroDTO.setTitulo("Introdução à Linguagem SQL");
        mockLivroDTO.setAutor("Thomas Nield");
        mockLivroDTO.setIsbn("9788575225011");
        mockLivroDTO.setDataPublicacao(LocalDate.of(2020, 01, 01));
        mockLivroDTO.setDescricaoCategoria("Tecnologia");
        mockLivroDTO.setEmprestado(false);

        when(livroService.findLivrosRecomendados(any(Pageable.class), anyLong()))
                .thenReturn(new PageImpl<>(List.of(mockLivroDTO)));

        mockMvc.perform(get("/api/livros/recomendacao/1?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(651))
                .andExpect(jsonPath("$.content[0].titulo").value("Introdução à Linguagem SQL"))
                .andExpect(jsonPath("$.content[0].autor").value("Thomas Nield"))
                .andExpect(jsonPath("$.content[0].isbn").value("9788575225011"))
                .andExpect(jsonPath("$.content[0].dataPublicacao").value("2020-01-01"))
                .andExpect(jsonPath("$.content[0].descricaoCategoria").value("Tecnologia"))
                .andExpect(jsonPath("$.content[0].emprestado").value("false"));
    }

    @Test
    public void testFindLivroByParam() throws Exception {
        LivroDTO mockLivroDTO = new LivroDTO();
        mockLivroDTO.setId(651L);
        mockLivroDTO.setTitulo("Introdução à Linguagem SQL");
        mockLivroDTO.setAutor("Thomas Nield");
        mockLivroDTO.setIsbn("9788575225011");
        mockLivroDTO.setDataPublicacao(LocalDate.of(2020, 01, 01));
        mockLivroDTO.setDescricaoCategoria("Tecnologia");
        mockLivroDTO.setEmprestado(false);

        when(livroService.findLivroByParam(any(Pageable.class), any(LivroParam.class)))
                .thenReturn(new PageImpl<>(List.of(mockLivroDTO)));

        mockMvc.perform(get("/api/livros/findByParam?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(651))
                .andExpect(jsonPath("$.content[0].titulo").value("Introdução à Linguagem SQL"))
                .andExpect(jsonPath("$.content[0].autor").value("Thomas Nield"))
                .andExpect(jsonPath("$.content[0].isbn").value("9788575225011"))
                .andExpect(jsonPath("$.content[0].dataPublicacao").value("2020-01-01"))
                .andExpect(jsonPath("$.content[0].descricaoCategoria").value("Tecnologia"))
                .andExpect(jsonPath("$.content[0].emprestado").value("false"));
    }

    @Test
    public void testDeleteLivro() throws Exception {
        doNothing().when(livroService).deleteLivro(1L);

        mockMvc.perform(delete("/api/livros/1"))
                .andExpect(status().isNoContent());
    }
}
