package com.atividadeoxy.biblioteca.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Class.Usuario;
import com.atividadeoxy.biblioteca.Repository.UsuarioRepository;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    private Usuario mockUsuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUsuario = new Usuario(
                1L,
                "Jobisnelson da Silva",
                "jobisnelson@biblioteca.com",
                new Timestamp(System.currentTimeMillis()),
                "4498165121");
    }

    @Test
    void testCreateUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(mockUsuario);

        Usuario usuario = usuarioService.createUsuario(mockUsuario);

        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("Jobisnelson da Silva");
        assertThat(usuario.getEmail()).isEqualTo("jobisnelson@biblioteca.com");
        assertThat(usuario.getTelefone()).isEqualTo("4498165121");
    }

    @Test
    void testFindAll() {
        Page<Usuario> mockPage = new PageImpl<>(List.of(mockUsuario));

        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Usuario> page = usuarioService.findAll(PageRequest.of(0, 10));

        assertEquals(1, page.getContent().size());
        assertEquals(1L, page.getContent().get(0).getId());
    }

    @Test
    void testFindById() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(mockUsuario));

        Optional<Usuario> usuario = usuarioService.findById(1L);

        assertTrue(usuario.isPresent());
        assertEquals(1L, usuario.get().getId());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(1L));
    }

    @Test
    void testFindUsuarioByParam() {
        UsuarioDTO mockUsuarioDTO = new UsuarioDTO();
        mockUsuarioDTO.setId(1L);
        mockUsuarioDTO.setNome("Jobisnelson da Silva");
        mockUsuarioDTO.setEmail("jobisnelson@biblioteca.com");
        mockUsuarioDTO.setTelefone("4498165121");
        mockUsuarioDTO.setQtdeLivrosPendentes(1L);
        mockUsuarioDTO.setDataCadastro(new Timestamp(System.currentTimeMillis()));

        Page<UsuarioDTO> mockPage = new PageImpl<>(List.of(mockUsuarioDTO));

        when(usuarioRepository.findUsuarioByParam(any(Pageable.class), any(UsuarioParam.class))).thenReturn(mockPage);

        UsuarioParam usuarioParam = new UsuarioParam();
        usuarioParam.setNome("jobisnelson");

        Page<UsuarioDTO> page = usuarioService.findUsuarioByParam(PageRequest.of(0, 10), usuarioParam);

        assertNotNull(page);
        assertEquals(1, page.getContent().size());

        UsuarioDTO usuarioDTO = page.getContent().get(0);
        assertThat(usuarioDTO.getId()).isEqualTo(1L);
        assertThat(usuarioDTO.getNome()).isEqualTo("Jobisnelson da Silva");
        assertThat(usuarioDTO.getEmail()).isEqualTo("jobisnelson@biblioteca.com");
        assertThat(usuarioDTO.getTelefone()).isEqualTo("4498165121");
        assertThat(usuarioDTO.getQtdeLivrosPendentes()).isEqualTo(1L);
    }
}
