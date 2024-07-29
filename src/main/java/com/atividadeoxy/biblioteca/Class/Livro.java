package com.atividadeoxy.biblioteca.Class;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro {
    @Id
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
