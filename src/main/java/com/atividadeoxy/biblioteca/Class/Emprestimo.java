package com.atividadeoxy.biblioteca.Class;

import java.time.LocalDate;

import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emprestimo {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
    private LocalDate data_emprestimo;
    private LocalDate data_devolucao;
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;
}
