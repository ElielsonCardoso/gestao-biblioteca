package com.atividadeoxy.biblioteca.Repository;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmprestimoRepositoryTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindRecomendacaoLivros() {
        RecomendacaoLivroDTO recomendacaoLivroDTO = new RecomendacaoLivroDTO();
        recomendacaoLivroDTO.setCategoriaId(1L);
        recomendacaoLivroDTO.setDescricaoCategoria("Tecnologia");
        recomendacaoLivroDTO.setQtdeEmprestada(5L);

        Page<RecomendacaoLivroDTO> mockPage = new PageImpl<>(List.of(recomendacaoLivroDTO));

        when(emprestimoRepository.findRecomendacaoLivros(any(PageRequest.class), anyLong()))
                .thenReturn(mockPage);

        Page<RecomendacaoLivroDTO> page = emprestimoRepository.findRecomendacaoLivros(PageRequest.of(0, 10), 1L);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        RecomendacaoLivroDTO recomendacao = page.getContent().get(0);
        assertThat(recomendacao.getCategoriaId()).isEqualTo(1L);
        assertThat(recomendacao.getQtdeEmprestada()).isEqualTo(5);
        assertThat(recomendacao.getDescricaoCategoria()).isEqualTo("Tecnologia");
    }

    @Test
    public void testFindEmprestimoByParam() {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setId(1L);
        emprestimoDTO.setUsuarioId(1L);
        emprestimoDTO.setNomeUsuario("Usuario Teste");
        emprestimoDTO.setLivroId(1L);
        emprestimoDTO.setTitulo("Java Programação");
        emprestimoDTO.setDataEmprestimo(LocalDate.now().minusDays(30));
        emprestimoDTO.setDataDevolucao(LocalDate.now());
        emprestimoDTO.setStatus(StatusEmprestimo.DEVOLVIDO);

        Page<EmprestimoDTO> mockPage = new PageImpl<>(List.of(emprestimoDTO));

        when(emprestimoRepository.findEmprestimoByParam(any(PageRequest.class), any(EmprestimoParam.class)))
                .thenReturn(mockPage);

        EmprestimoParam param = new EmprestimoParam();
        param.setUsuarioId(1L);

        Page<EmprestimoDTO> page = emprestimoRepository.findEmprestimoByParam(PageRequest.of(0, 10), param);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        EmprestimoDTO emprestimo = page.getContent().get(0);
        assertThat(emprestimo.getId()).isEqualTo(1L);
        assertThat(emprestimo.getUsuarioId()).isEqualTo(1L);
        assertThat(emprestimo.getNomeUsuario()).isEqualTo("Usuario Teste");
        assertThat(emprestimo.getLivroId()).isEqualTo(1L);
        assertThat(emprestimo.getTitulo()).isEqualTo("Java Programação");
        assertThat(emprestimo.getDataEmprestimo()).isEqualTo(LocalDate.now().minusDays(30));
        assertThat(emprestimo.getDataDevolucao()).isEqualTo(LocalDate.now());
        assertThat(emprestimo.getStatus()).isEqualTo(StatusEmprestimo.DEVOLVIDO);
    }
}
