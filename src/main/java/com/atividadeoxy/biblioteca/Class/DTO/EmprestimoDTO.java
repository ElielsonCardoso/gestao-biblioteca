package com.atividadeoxy.biblioteca.Class.DTO;

import java.sql.Date;
import java.time.LocalDate;

import com.atividadeoxy.biblioteca.Class.Enum.StatusEmprestimo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmprestimoDTO {
    private Long id;
    private Long usuarioId;
    private String nomeUsuario;
    private Long livroid;
    private String titulo;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

    public static EmprestimoDTO resultToEmprestimoDTO(Object[] result) {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setId(((Number) result[0]).longValue());
        emprestimoDTO.setUsuarioId(((Number) result[1]).longValue());
        emprestimoDTO.setNomeUsuario((String) result[2]);
        emprestimoDTO.setLivroid(((Number) result[3]).longValue());
        emprestimoDTO.setTitulo((String) result[4]);
        Date dataEmprestimo = (Date) result[5];
        emprestimoDTO.setDataEmprestimo(dataEmprestimo.toLocalDate());
        if (result[6] != null){
            Date dataDevolucao = (Date) result[6];
            emprestimoDTO.setDataDevolucao(dataDevolucao.toLocalDate());
        }
        emprestimoDTO.setStatus(StatusEmprestimo.valueOf((String) result[7]));
        return emprestimoDTO;
    }
}
