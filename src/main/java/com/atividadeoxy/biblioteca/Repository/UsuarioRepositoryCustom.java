package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;

public interface UsuarioRepositoryCustom {
    Page<UsuarioDTO> findUsuarioByParam(Pageable pageable, UsuarioParam usuarioParam);
}
