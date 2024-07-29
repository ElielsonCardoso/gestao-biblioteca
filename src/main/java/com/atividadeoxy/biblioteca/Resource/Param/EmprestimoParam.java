package com.atividadeoxy.biblioteca.Resource.Param;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoParam {
    private Long usuarioId;
    private String nomeUsuario;
    private Long livroId;
    private String titulo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataEmprestimo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDevolucao;
    private String status;
}
