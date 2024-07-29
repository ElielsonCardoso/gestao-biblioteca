package com.atividadeoxy.biblioteca.Resource;

import java.sql.Timestamp;
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
import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Class.Emprestimo;
import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Class.Usuario;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;
import com.atividadeoxy.biblioteca.Service.EmprestimoService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmprestimoResource.class)
public class EmprestimoResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmprestimoService emprestimoService;

    private Usuario mockUsuario;

    private Categoria mockCategoria;
    private Livro mockLivro;

    private Emprestimo mockEmprestimo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockCategoria = new Categoria(
                1L,
                "Tecnologia");

        mockUsuario = new Usuario(
                1L,
                "Kleberson Josildo",
                "kleb@biblioteca.com",
                new Timestamp(System.currentTimeMillis()),
                "448916515165");

        mockLivro = new Livro(
                1L,
                "Programação Java Básico",
                "Leandro Hossi",
                "519816531321",
                LocalDate.now().minusYears(5L),
                mockCategoria);

        mockEmprestimo = new Emprestimo(
                1L,
                mockUsuario,
                mockLivro,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 20),
                StatusEmprestimo.DEVOLVIDO);
    }

    @Test
    public void testCreateEmprestimo() throws Exception {
        when(emprestimoService.createEmprestimo(any(Emprestimo.class))).thenReturn(mockEmprestimo);

        mockMvc.perform(post("/api/emprestimos")
                        .contentType("application/json")
                        .content("{\"id\":1,\"dataEmprestimo\":\"2024-01-01\",\"dataDevolucao\":\"2024-02-01\",\"status\":\"DEVOLVIDO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dataEmprestimo").value("2024-01-01"))
                .andExpect(jsonPath("$.dataDevolucao").value("2024-02-20"))
                .andExpect(jsonPath("$.status").value("DEVOLVIDO"));
    }

    @Test
    public void testFindAll() throws Exception {
        when(emprestimoService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(mockEmprestimo)));

        mockMvc.perform(get("/api/emprestimos?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].dataEmprestimo").value("2024-01-01"))
                .andExpect(jsonPath("$.content[0].dataDevolucao").value("2024-02-20"))
                .andExpect(jsonPath("$.content[0].status").value("DEVOLVIDO"));
    }

    @Test
    public void testFindById() throws Exception {
        when(emprestimoService.findById(1L)).thenReturn(Optional.of(mockEmprestimo));

        mockMvc.perform(get("/api/emprestimos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dataEmprestimo").value("2024-01-01"))
                .andExpect(jsonPath("$.dataDevolucao").value("2024-02-20"))
                .andExpect(jsonPath("$.status").value("DEVOLVIDO"));
    }

    @Test
    public void testFindRecomendacaoLivros() throws Exception {
        RecomendacaoLivroDTO mockDTO = new RecomendacaoLivroDTO();
        mockDTO.setQtdeEmprestada(32L);
        mockDTO.setCategoriaId(2L);
        mockDTO.setDescricaoCategoria("Ficção");

        when(emprestimoService.findRecomendacaoLivros(any(Pageable.class), anyLong()))
                .thenReturn(new PageImpl<>(List.of(mockDTO)));

        mockMvc.perform(get("/api/emprestimos/recomendacao/1?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].categoriaId").value(2))
                .andExpect(jsonPath("$.content[0].qtdeEmprestada").value(32))
                .andExpect(jsonPath("$.content[0].descricaoCategoria").value("Ficção"));
    }

    @Test
    public void testFindEmprestimoByParam() throws Exception {
        EmprestimoDTO mockDTO = new EmprestimoDTO();
        mockDTO.setId(1L);
        mockDTO.setUsuarioId(35L);
        mockDTO.setNomeUsuario("Jobisnelson Lavine");
        mockDTO.setLivroId(65L);
        mockDTO.setTitulo("Mineração de bitcoin em casa");
        mockDTO.setDataEmprestimo(LocalDate.now());
        mockDTO.setStatus(StatusEmprestimo.EMPRESTADO);

        when(emprestimoService.findEmprestimoByParam(any(Pageable.class), any(EmprestimoParam.class)))
                .thenReturn(new PageImpl<>(List.of(mockDTO)));

        mockMvc.perform(get("/api/emprestimos/findByParam?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].usuarioId").value(35))
                .andExpect(jsonPath("$.content[0].nomeUsuario").value("Jobisnelson Lavine"))
                .andExpect(jsonPath("$.content[0].livroId").value(65))
                .andExpect(jsonPath("$.content[0].titulo").value("Mineração de bitcoin em casa"))
                .andExpect(jsonPath("$.content[0].status").value("EMPRESTADO"));
    }

    @Test
    public void testDevolverEmprestimo() throws Exception {
        when(emprestimoService.devolverEmprestimo(1L)).thenReturn(mockEmprestimo);

        mockMvc.perform(put("/api/emprestimos/devolver/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.dataEmprestimo").value("2024-01-01"))
                .andExpect(jsonPath("$.dataDevolucao").value("2024-02-20"))
                .andExpect(jsonPath("$.status").value("DEVOLVIDO"));
    }

    @Test
    public void testDeleteEmprestimo() throws Exception {
        doNothing().when(emprestimoService).deleteEmprestimo(1L);

        mockMvc.perform(delete("/api/emprestimos/1"))
                .andExpect(status().isNoContent());
    }

}
