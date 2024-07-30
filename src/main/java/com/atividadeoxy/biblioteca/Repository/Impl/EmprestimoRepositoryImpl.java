package com.atividadeoxy.biblioteca.Repository.Impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.atividadeoxy.biblioteca.Class.DTO.EmprestimoDTO;
import com.atividadeoxy.biblioteca.Class.DTO.RecomendacaoLivroDTO;
import com.atividadeoxy.biblioteca.Resource.Param.EmprestimoParam;
import com.atividadeoxy.biblioteca.Repository.EmprestimoRepositoryCustom;

public class EmprestimoRepositoryImpl implements EmprestimoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RecomendacaoLivroDTO> findRecomendacaoLivros(Pageable pageable, Long usuarioId) {
        StringBuilder sql = new StringBuilder(getSqlRecomendacaoLivro());

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("usuarioId", usuarioId);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        int count = results.size();
        List<RecomendacaoLivroDTO> recomendacoes = new ArrayList<>();

        for (Object[] result : results) {
            recomendacoes.add(RecomendacaoLivroDTO.resultToRecomendacaoLivroDTO(result));
        }

        return new PageImpl<>(recomendacoes, pageable, count);
    }

    @Override
    public Page<EmprestimoDTO> findEmprestimoByParam(Pageable pageable, EmprestimoParam emprestimoParam) {
        StringBuilder sql = new StringBuilder(getSqlFindEmprestimo());
        sql.append(getWhere(emprestimoParam));

        Query query = entityManager.createNativeQuery(sql.toString());

        setParametros(query, emprestimoParam);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        int count = results.size();
        List<EmprestimoDTO> emprestimos = new ArrayList<>();

        for (Object[] result : results) {
            emprestimos.add(EmprestimoDTO.resultToEmprestimoDTO(result));
        }

        return new PageImpl<>(emprestimos, pageable, count);
    }

    @Override
    public Long getProximoId() {
        String sql = getSqlProximoId();
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return ((Number) result).longValue();
    }

    private void setParametros(Query query, EmprestimoParam emprestimoParam) {
        if (emprestimoParam.getUsuarioId() != null) {
            query.setParameter("usuarioId", emprestimoParam.getUsuarioId());
        }

        if (emprestimoParam.getNomeUsuario() != null) {
            query.setParameter("nomeUsuario", "%" + emprestimoParam.getNomeUsuario().toUpperCase() + "%");
        }

        if (emprestimoParam.getLivroId() != null) {
            query.setParameter("livroId", emprestimoParam.getLivroId());
        }

        if (emprestimoParam.getTitulo() != null) {
            query.setParameter("titulo", "%" + emprestimoParam.getTitulo().toUpperCase() + "%");
        }

        if (emprestimoParam.getDataEmprestimo() != null) {
            query.setParameter("dataEmprestimo", Date.valueOf(emprestimoParam.getDataEmprestimo()));
        }

        if (emprestimoParam.getDataDevolucao() != null) {
            query.setParameter("dataDevolucao", Date.valueOf(emprestimoParam.getDataDevolucao()));
        }

        if (emprestimoParam.getStatus() != null) {
            query.setParameter("status", emprestimoParam.getStatus());
        }
    }

    private String getSqlRecomendacaoLivro() {
        return "SELECT X.CATEGORIA_ID AS CATEGORIAID, " +
               "       X.QTDEEMPRESTADA AS QTDEEMPRESTADA, " +
               "       C.DESCRICAO AS DESCRICAOCATEGORIA " +
               "  FROM ( " +
               "    SELECT L.CATEGORIA_ID, " +
               "           COUNT(L.CATEGORIA_ID) AS QTDEEMPRESTADA " +
               "      FROM EMPRESTIMO E " +
               "      JOIN LIVRO L ON L.ID = E.LIVRO_ID " +
               "     WHERE E.USUARIO_ID = :usuarioId " +
               "     GROUP BY L.CATEGORIA_ID " +
               "     ORDER BY QTDEEMPRESTADA DESC" +
               "     LIMIT 1" +
               ") X " +
               "JOIN CATEGORIA C ON C.ID = X.CATEGORIA_ID ";

    }

    private String getSqlFindEmprestimo() {
        return "SELECT E.ID, " +
               "       E.USUARIO_ID AS USUARIOID, " +
               "       U.NOME AS NOMEUSUARIO, " +
               "       E.LIVRO_ID AS LIVROID, " +
               "       L.TITULO, " +
               "       E.DATA_EMPRESTIMO AS DATAEMPRESTIMO, " +
               "       E.DATA_DEVOLUCAO AS DATADEVOLUCAO, " +
               "       E.STATUS " +
               "  FROM EMPRESTIMO E " +
               "  JOIN LIVRO L ON L.ID = E.LIVRO_ID " +
               "  JOIN USUARIO U ON U.ID = E.USUARIO_ID " +
               " WHERE 1=1 ";
    }

    private String getWhere(EmprestimoParam emprestimoParam) {
        StringBuilder sqlWhere = new StringBuilder();

        if (emprestimoParam.getUsuarioId() != null) {
            sqlWhere.append(" AND E.USUARIO_ID = :usuarioId ");
        }

        if (emprestimoParam.getNomeUsuario() != null) {
            sqlWhere.append(" AND UPPER(U.NOME) LIKE :nomeUsuario ");
        }

        if (emprestimoParam.getLivroId() != null) {
            sqlWhere.append(" AND E.LIVRO_ID = :livroId ");
        }

        if (emprestimoParam.getTitulo() != null) {
            sqlWhere.append(" AND UPPER(L.TITULO) LIKE :titulo ");
        }

        if (emprestimoParam.getDataEmprestimo() != null) {
            sqlWhere.append(" AND E.DATA_EMPRESTIMO = :dataEmprestimo ");
        }

        if (emprestimoParam.getDataDevolucao() != null) {
            sqlWhere.append(" AND E.DATA_DEVOLUCAO = :dataDevolucao ");
        }

        if (emprestimoParam.getStatus() != null) {
            sqlWhere.append(" AND E.STATUS = :status ");
        }

        return sqlWhere.toString();
    }

    private String getSqlProximoId() {
        return "SELECT COALESCE(MAX(E.ID), 0)+1 ID " +
                "  FROM EMPRESTIMO E ";
    }
}
