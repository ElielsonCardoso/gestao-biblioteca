package com.atividadeoxy.biblioteca.Class.DTO;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private LocalDate dataPublicacao;
    private String descricaoCategoria;
    private Boolean emprestado;

    public static LivroDTO resultToLivroDTO(Object[] result) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(((Number) result[0]).longValue());
        livroDTO.setTitulo(((String) result[1]));
        livroDTO.setAutor(((String) result[2]));
        livroDTO.setIsbn(((String) result[3]));
        Date dataPublicacao = (Date) result[4];
        livroDTO.setDataPublicacao(dataPublicacao.toLocalDate());
        livroDTO.setDescricaoCategoria(((String) result[5]));
        String emprestado = (String) result[6];
        livroDTO.setEmprestado("SIM".equals(emprestado));

        return livroDTO;
    }
}
