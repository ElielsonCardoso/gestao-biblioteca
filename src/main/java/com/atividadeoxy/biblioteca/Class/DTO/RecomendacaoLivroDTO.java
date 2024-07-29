package com.atividadeoxy.biblioteca.Class.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecomendacaoLivroDTO {
    private Long categoriaId;
    private Long qtdeEmprestada;
    private String descricaoCategoria;

    public static RecomendacaoLivroDTO resultToRecomendacaoLivroDTO(Object[] result) {
        RecomendacaoLivroDTO recomendacaoLivroDTO = new RecomendacaoLivroDTO();
        recomendacaoLivroDTO.setCategoriaId(((Number) result[0]).longValue());
        recomendacaoLivroDTO.setQtdeEmprestada(((Number) result[1]).longValue());
        recomendacaoLivroDTO.setDescricaoCategoria(((String) result[2]));
        return recomendacaoLivroDTO;
    }
}
