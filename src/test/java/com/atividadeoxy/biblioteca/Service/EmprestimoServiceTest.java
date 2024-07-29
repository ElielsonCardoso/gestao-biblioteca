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

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Class.Emprestimo;
import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;
import com.atividadeoxy.biblioteca.Class.Livro;
import com.atividadeoxy.biblioteca.Class.Usuario;
import com.atividadeoxy.biblioteca.Repository.EmprestimoRepository;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @InjectMocks
    private EmprestimoService emprestimoService;

    private Usuario mockUsuario;

    private Livro mockLivro;

    private Emprestimo mockEmprestimo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUsuario = mock(Usuario.class);
        mockLivro = mock(Livro.class);
        mockEmprestimo = new Emprestimo(
                1L,
                mockUsuario,
                mockLivro,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 2, 20),
                StatusEmprestimo.DEVOLVIDO);
    }

    @Test
    void testCreateEmprestimo() {
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(mockEmprestimo);

        Emprestimo emprestimo = emprestimoService.createEmprestimo(mockEmprestimo);

        assertEquals(1L, emprestimo.getId());
        assertEquals(LocalDate.of(2024, 1, 1), emprestimo.getDataEmprestimo());
        assertEquals(LocalDate.of(2024, 2, 20), emprestimo.getDataDevolucao());
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimo.getStatus());
    }

    @Test
    void testFindAll() {
        Page<Emprestimo> mockPage = new PageImpl<>(List.of(mockEmprestimo));

        when(emprestimoRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Emprestimo> page = emprestimoService.findAll(PageRequest.of(0, 10));

        assertEquals(1, page.getContent().size());
        assertEquals(1L, page.getContent().get(0).getId());
    }

    @Test
    void testFindById() {
        when(emprestimoRepository.findById(anyLong())).thenReturn(Optional.of(mockEmprestimo));

        Optional<Emprestimo> emprestimo = emprestimoService.findById(1L);

        assertTrue(emprestimo.isPresent());
        assertEquals(1L, emprestimo.get().getId());
    }

    @Test
    void testDevolverEmprestimo() throws Exception {
        Emprestimo mockEmprestimoDevolvido = new Emprestimo(
                698L,
                mockUsuario,
                mockLivro,
                LocalDate.of(2024, 1, 1),
                LocalDate.now(),
                StatusEmprestimo.DEVOLVIDO);

        when(emprestimoRepository.findById(anyLong())).thenReturn(Optional.of(mockEmprestimo));
        when(emprestimoRepository.save(any(Emprestimo.class))).thenReturn(mockEmprestimoDevolvido);

        Emprestimo emprestimoDevolvido = emprestimoService.devolverEmprestimo(1L);

        assertNotNull(emprestimoDevolvido);
        assertEquals(StatusEmprestimo.DEVOLVIDO, emprestimoDevolvido.getStatus());
        assertNotNull(emprestimoDevolvido.getDataDevolucao());
    }

    @Test
    void testDeleteEmprestimo() {
        doNothing().when(emprestimoRepository).deleteById(1L);

        assertDoesNotThrow(() -> emprestimoService.deleteEmprestimo(1L));
    }

    @Test
    void testFindRecomendacaoLivros() {
        RecomendacaoLivroDTO dto = new RecomendacaoLivroDTO();
        dto.setQtdeEmprestada(32L);
        dto.setCategoriaId(2L);
        dto.setDescricaoCategoria("Ficção");

        Page<RecomendacaoLivroDTO> mockPage = new PageImpl<>(List.of(dto));

        when(emprestimoRepository.findRecomendacaoLivros(any(Pageable.class), anyLong())).thenReturn(mockPage);

        Page<RecomendacaoLivroDTO> page = emprestimoService.findRecomendacaoLivros(PageRequest.of(0, 10), 1L);

        assertNotNull(page);
        assertEquals(1, page.getContent().size());

        RecomendacaoLivroDTO recomendacaoLivroDTO = page.getContent().get(0);
        assertEquals(2L, recomendacaoLivroDTO.getCategoriaId());
        assertEquals(32L, recomendacaoLivroDTO.getQtdeEmprestada());
        assertEquals("Ficção", recomendacaoLivroDTO.getDescricaoCategoria());
    }

    @Test
    void testFindEmprestimoByParam() {
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setId(1L);
        dto.setUsuarioId(35L);
        dto.setNomeUsuario("Jobisnelson Lavine");
        dto.setLivroId(65L);
        dto.setTitulo("Mineração de bitcoin em casa");
        dto.setDataEmprestimo(LocalDate.now());
        dto.setStatus(StatusEmprestimo.EMPRESTADO);

        Page<EmprestimoDTO> mockPage = new PageImpl<>(List.of(dto));

        when(emprestimoRepository.findEmprestimoByParam(any(Pageable.class), any(EmprestimoParam.class))).thenReturn(mockPage);

        EmprestimoParam param = new EmprestimoParam();
        param.setUsuarioId(1L);

        Page<EmprestimoDTO> page = emprestimoService.findEmprestimoByParam(PageRequest.of(0, 10), param);

        assertEquals(1, page.getContent().size());

        EmprestimoDTO emprestimoDTO = page.getContent().get(0);
        assertEquals(1L, emprestimoDTO.getId());
        assertEquals(35L, emprestimoDTO.getUsuarioId());
        assertEquals("Jobisnelson Lavine", emprestimoDTO.getNomeUsuario());
        assertEquals(65L, emprestimoDTO.getLivroId());
        assertEquals("Mineração de bitcoin em casa", emprestimoDTO.getTitulo());
        assertEquals(LocalDate.now(), emprestimoDTO.getDataEmprestimo());
        assertEquals(StatusEmprestimo.EMPRESTADO, emprestimoDTO.getStatus());

    }

}
