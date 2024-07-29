package com.atividadeoxy.biblioteca.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;

public interface EmprestimoRepositoryCustom {
    Page<RecomendacaoLivroDTO> findRecomendacaoLivros(Pageable pageable, Long usuarioId);

    Page<EmprestimoDTO> findEmprestimoByParam(Pageable pageable, EmprestimoParam emprestimoParam);
}
