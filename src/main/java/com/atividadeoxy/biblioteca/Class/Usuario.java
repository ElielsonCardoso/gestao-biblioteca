package com.atividadeoxy.biblioteca.Class;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    private Long id;
    private String nome;
    private String email;
    @Column(name = "data_cadastro")
    private Timestamp dataCadastro;
    private String telefone;

}
