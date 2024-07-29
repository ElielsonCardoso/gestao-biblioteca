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

import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LivroRepositoryTest {

    @Mock
    LivroRepository livroRepository;

    private LivroDTO livroDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Introdução à Linguagem SQL");
        livroDTO.setAutor("Thomas Nield");
        livroDTO.setIsbn("9788575225011");
        livroDTO.setDataPublicacao(LocalDate.now().minusYears(10));
        livroDTO.setDescricaoCategoria("Tecnologia");
        livroDTO.setEmprestado(true);
    }

    @Test
    public void testFindLivroByParam(){
        Page<LivroDTO> mockPage = new PageImpl<>(List.of(livroDTO));

        when(livroRepository.findLivroByParam(any(PageRequest.class), any(LivroParam.class)))
                .thenReturn(mockPage);

        LivroParam livroParam = new LivroParam();
        livroParam.setAutor("thomas");

        Page<LivroDTO> page = livroRepository.findLivroByParam(PageRequest.of(0, 10), livroParam);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        LivroDTO livro = page.getContent().get(0);
        assertThat(livro.getId()).isEqualTo(1L);
        assertThat(livro.getTitulo()).isEqualTo("Introdução à Linguagem SQL");
        assertThat(livro.getAutor()).isEqualTo("Thomas Nield");
        assertThat(livro.getIsbn()).isEqualTo("9788575225011");
        assertThat(livro.getDataPublicacao()).isEqualTo(LocalDate.now().minusYears(10));
        assertThat(livro.getDescricaoCategoria()).isEqualTo("Tecnologia");
        assertThat(livro.getEmprestado()).isEqualTo(true);
    }

    @Test
    public void testFindLivrosRecomendados(){
        Page<LivroDTO> mockPage = new PageImpl<>(List.of(livroDTO));

        when(livroRepository.findLivrosRecomendados(any(PageRequest.class), anyLong()))
                .thenReturn(mockPage);

        Page<LivroDTO> page = livroRepository.findLivrosRecomendados(PageRequest.of(0, 10), 1L);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        LivroDTO livro = page.getContent().get(0);
        assertThat(livro.getId()).isEqualTo(1L);
        assertThat(livro.getTitulo()).isEqualTo("Introdução à Linguagem SQL");
        assertThat(livro.getAutor()).isEqualTo("Thomas Nield");
        assertThat(livro.getIsbn()).isEqualTo("9788575225011");
        assertThat(livro.getDataPublicacao()).isEqualTo(LocalDate.now().minusYears(10));
        assertThat(livro.getDescricaoCategoria()).isEqualTo("Tecnologia");
        assertThat(livro.getEmprestado()).isEqualTo(true);
    }
}
