package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;

public interface LivroRepositoryCustom {
    Page<LivroDTO> findLivroByParam(Pageable pageable, LivroParam livroParam);
}
