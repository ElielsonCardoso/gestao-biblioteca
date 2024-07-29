package com.atividadeoxy.biblioteca.Resource.Param;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioParam {
    private String nome;
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataCadastro;
    private String telefone;
}
