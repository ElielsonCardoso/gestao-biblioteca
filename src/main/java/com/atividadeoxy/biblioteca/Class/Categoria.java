package com.atividadeoxy.biblioteca.Class;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Categoria {
    @Id
    private Long id;
    private String descricao;
}
