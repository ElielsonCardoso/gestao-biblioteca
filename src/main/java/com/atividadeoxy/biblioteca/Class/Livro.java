package com.atividadeoxy.biblioteca.Class;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Livro {
    @Id
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
