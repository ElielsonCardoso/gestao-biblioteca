package com.atividadeoxy.biblioteca.Resource;

import java.sql.Timestamp;
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

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Class.Usuario;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;
import com.atividadeoxy.biblioteca.Service.UsuarioService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioResource.class)
public class UsuarioResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    public void testCreateUsuario() throws Exception {
        when(usuarioService.createUsuario(any(Usuario.class))).thenReturn(mockUsuario);

        mockMvc.perform(post("/api/usuarios")
                        .contentType("application/json")
                        .content("{\"id\":1,\"nome\":\"Jobisnelson da Silva\",\"email\":\"jobisnelson@biblioteca.com\",\"telefone\":\"4498165121\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Jobisnelson da Silva"))
                .andExpect(jsonPath("$.email").value("jobisnelson@biblioteca.com"))
                .andExpect(jsonPath("$.telefone").value("4498165121"));
    }

    @Test
    public void testFindAll() throws Exception {
        when(usuarioService.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(mockUsuario)));

        mockMvc.perform(get("/api/usuarios?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Jobisnelson da Silva"))
                .andExpect(jsonPath("$.content[0].email").value("jobisnelson@biblioteca.com"))
                .andExpect(jsonPath("$.content[0].telefone").value("4498165121"));
    }

    @Test
    public void testFindById() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(Optional.of(mockUsuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Jobisnelson da Silva"))
                .andExpect(jsonPath("$.email").value("jobisnelson@biblioteca.com"))
                .andExpect(jsonPath("$.telefone").value("4498165121"));
    }

    @Test
    public void testFindUsuarioByParam() throws Exception {
        UsuarioDTO mockUsuarioDTO = new UsuarioDTO();
        mockUsuarioDTO.setId(1L);
        mockUsuarioDTO.setNome("Jobisnelson da Silva");
        mockUsuarioDTO.setEmail("jobisnelson@biblioteca.com");
        mockUsuarioDTO.setTelefone("4498165121");
        mockUsuarioDTO.setQtdeLivrosPendentes(8L);
        mockUsuarioDTO.setDataCadastro(new Timestamp(System.currentTimeMillis()));

        when(usuarioService.findUsuarioByParam(any(Pageable.class), any(UsuarioParam.class)))
                .thenReturn(new PageImpl<>(List.of(mockUsuarioDTO)));

        mockMvc.perform(get("/api/usuarios/findByParam?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Jobisnelson da Silva"))
                .andExpect(jsonPath("$.content[0].email").value("jobisnelson@biblioteca.com"))
                .andExpect(jsonPath("$.content[0].telefone").value("4498165121"))
                .andExpect(jsonPath("$.content[0].qtdeLivrosPendentes").value(8));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).deleteUsuario(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

}
