package com.atividadeoxy.biblioteca.Resource.Param;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroParam {
    private String titulo;
    private String autor;
    private String isbn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataPublicacao;
    private String descricaoCategoria;
    private Boolean emprestado;
}
