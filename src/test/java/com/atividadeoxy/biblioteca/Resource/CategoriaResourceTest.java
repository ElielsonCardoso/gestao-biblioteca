package com.atividadeoxy.biblioteca.Resource;

import com.atividadeoxy.biblioteca.Class.Categoria;
import com.atividadeoxy.biblioteca.Service.CategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CategoriaResource.class)
public class CategoriaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    public void testFindAll() throws Exception {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setDescricao("Ficção");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setDescricao("Não-ficção");

        List<Categoria> categoriasMock = Arrays.asList(categoria1, categoria2);

        when(categoriaService.findAll()).thenReturn(categoriasMock);

        mockMvc.perform(get("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'descricao':'Ficção'},{'id':2,'descricao':'Não-ficção'}]"));

        verify(categoriaService, times(1)).findAll();
    }
}
