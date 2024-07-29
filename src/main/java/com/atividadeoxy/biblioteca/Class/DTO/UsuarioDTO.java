package com.atividadeoxy.biblioteca.Class.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private Timestamp dataCadastro;
    private String telefone;
    private Long qtdeLivrosPendentes;

    public static UsuarioDTO resultToUsuarioDTO(Object[] result) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(((Number) result[0]).longValue());
        usuarioDTO.setNome(((String) result[1]));
        usuarioDTO.setEmail(((String) result[2]));
        Timestamp dataCadastro = (Timestamp) result[3];
        usuarioDTO.setDataCadastro(dataCadastro);
        usuarioDTO.setTelefone(((String) result[4]));
        usuarioDTO.setQtdeLivrosPendentes(((Number) result[5]).longValue());

        return usuarioDTO;
    }
}
