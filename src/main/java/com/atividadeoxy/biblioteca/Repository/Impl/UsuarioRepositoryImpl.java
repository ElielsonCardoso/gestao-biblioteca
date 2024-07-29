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

import com.atividadeoxy.biblioteca.Class.DTO.UsuarioDTO;
import com.atividadeoxy.biblioteca.Repository.UsuarioRepositoryCustom;
import com.atividadeoxy.biblioteca.Resource.Param.UsuarioParam;

public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<UsuarioDTO> findUsuarioByParam(Pageable pageable, UsuarioParam usuarioParam) {
        StringBuilder sql = new StringBuilder(getSqlFindUsuario());
        sql.append(getWhere(usuarioParam));

        Query query = entityManager.createNativeQuery(sql.toString());

        setParametros(query, usuarioParam);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        int count = results.size();
        List<UsuarioDTO> usuarios = new ArrayList<>();

        for (Object[] result : results) {
            usuarios.add(UsuarioDTO.resultToUsuarioDTO(result));
        }

        return new PageImpl<>(usuarios, pageable, count);
    }

    private void setParametros(Query query, UsuarioParam usuarioParam) {
        if (usuarioParam.getNome() != null) {
            query.setParameter("nome", "%" + usuarioParam.getNome().toUpperCase() + "%");
        }

        if (usuarioParam.getEmail() != null) {
            query.setParameter("email", "%" + usuarioParam.getEmail().toUpperCase() + "%");
        }

        if (usuarioParam.getDataCadastro() != null) {
            query.setParameter("dataCadastro", Date.valueOf(usuarioParam.getDataCadastro()));
        }

        if (usuarioParam.getTelefone() != null) {
            query.setParameter("telefone", "%" + usuarioParam.getTelefone() + "%");
        }
    }

    private String getSqlFindUsuario() {
        return "SELECT U.ID, " +
               "       U.NOME, " +
               "       U.EMAIL, " +
               "       U.DATA_CADASTRO AS DATACADASTRO, " +
               "       U.TELEFONE, " +
               "       (SELECT COALESCE(COUNT(E.ID), 0) " +
               "          FROM EMPRESTIMO E " +
               "         WHERE E.DATA_DEVOLUCAO IS NULL " +
               "           AND E.USUARIO_ID = U.ID " +
               "       ) AS QTDELIVROSPENDENTES " +
               "  FROM USUARIO U " +
               " WHERE 1=1 ";
    }

    private String getWhere(UsuarioParam usuarioParam) {
        StringBuilder sqlWhere = new StringBuilder();

        if (usuarioParam.getNome() != null) {
            sqlWhere.append(" AND UPPER(U.NOME) LIKE :nome ");
        }

        if (usuarioParam.getEmail() != null) {
            sqlWhere.append(" AND UPPER(U.EMAIL) LIKE :email ");
        }

        if (usuarioParam.getDataCadastro() != null) {
            sqlWhere.append(" AND DATE(U.DATA_CADASTRO) = :dataCadastro ");
        }

        if (usuarioParam.getTelefone() != null) {
            sqlWhere.append(" AND U.TELEFONE LIKE :telefone ");
        }

        return sqlWhere.toString();
    }
}
