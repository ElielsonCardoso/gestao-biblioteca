package com.atividadeoxy.biblioteca.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioRepositoryTest {

    @Mock
    UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNome("Jobisnelson da Silva");
        usuarioDTO.setEmail("jobisnelson@biblioteca.com");
        usuarioDTO.setTelefone("4498165121");
        usuarioDTO.setQtdeLivrosPendentes(1L);
        usuarioDTO.setDataCadastro(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void testFindUsuarioByParam(){
        Page<UsuarioDTO> mockPage = new PageImpl<>(List.of(usuarioDTO));

        when(usuarioRepository.findUsuarioByParam(any(PageRequest.class), any(UsuarioParam.class)))
                .thenReturn(mockPage);

        UsuarioParam param = new UsuarioParam();
        param.setNome("jobisnelson");

        Page<UsuarioDTO> page = usuarioRepository.findUsuarioByParam(PageRequest.of(0, 10), param);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent().size()).isEqualTo(1);

        UsuarioDTO usuario = page.getContent().get(0);
        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("Jobisnelson da Silva");
        assertThat(usuario.getEmail()).isEqualTo("jobisnelson@biblioteca.com");
        assertThat(usuario.getTelefone()).isEqualTo("4498165121");
        assertThat(usuario.getQtdeLivrosPendentes()).isEqualTo(1L);
    }
}
