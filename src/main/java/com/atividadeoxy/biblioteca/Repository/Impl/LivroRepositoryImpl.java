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

import com.atividadeoxy.biblioteca.Class.DTO.LivroDTO;
import com.atividadeoxy.biblioteca.Repository.LivroRepositoryCustom;
import com.atividadeoxy.biblioteca.Resource.Param.LivroParam;

public class LivroRepositoryImpl implements LivroRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<LivroDTO> findLivroByParam(Pageable pageable, LivroParam livroParam) {
        StringBuilder sql = new StringBuilder(getSqlFindLivro());
        sql.append(getWhere(livroParam));

        Query query = entityManager.createNativeQuery(sql.toString());

        setParametros(query, livroParam);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        int count = results.size();
        List<LivroDTO> livros = new ArrayList<>();

        for (Object[] result : results) {
            livros.add(LivroDTO.resultToLivroDTO(result));
        }

        return new PageImpl<>(livros, pageable, count);
    }

    @Override
    public Page<LivroDTO> findLivrosRecomendados(Pageable pageable, Long usuarioId) {
        StringBuilder sql = new StringBuilder(getSqlFindLivrosRecomendados());

        Query query = entityManager.createNativeQuery(sql.toString());

        query.setParameter("usuarioId", usuarioId);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        int count = results.size();
        List<LivroDTO> livros = new ArrayList<>();

        for (Object[] result : results) {
            livros.add(LivroDTO.resultToLivroDTO(result));
        }

        return new PageImpl<>(livros, pageable, count);
    }

    @Override
    public Long getProximoId() {
        String sql = getSqlProximoId();
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        return ((Number) result).longValue();
    }

    private void setParametros(Query query, LivroParam livroParam) {
        if (livroParam.getTitulo() != null) {
            query.setParameter("titulo", "%" + livroParam.getTitulo().toUpperCase() + "%");
        }

        if (livroParam.getAutor() != null) {
            query.setParameter("autor", "%" + livroParam.getAutor().toUpperCase() + "%");
        }

        if (livroParam.getIsbn() != null) {
            query.setParameter("isbn", "%" + livroParam.getIsbn() + "%");
        }

        if (livroParam.getDataPublicacao() != null) {
            query.setParameter("dataPublicacao", Date.valueOf(livroParam.getDataPublicacao()));
        }

        if (livroParam.getDescricaoCategoria() != null) {
            query.setParameter("descricaoCategoria", "%" + livroParam.getDescricaoCategoria().toUpperCase() + "%");
        }
    }

    private String getSqlFindLivro() {
        return "SELECT L.ID, " +
               "       L.TITULO, "+
               "       L.AUTOR, "+
               "       L.ISBN, "+
               "       L.DATA_PUBLICACAO AS DATAPUBLICACAO, "+
               "       C.DESCRICAO AS DESCRICAOCATEGORIA, "+
               "       CASE WHEN EXISTS (SELECT E.ID "+
               "                          FROM EMPRESTIMO E "+
               "                         WHERE E.LIVRO_ID = L.ID "+
               "                           AND E.DATA_DEVOLUCAO IS NULL) "+
               "             THEN 'SIM' ELSE 'NAO' "+
               "       END AS EMPRESTADO "+
               "  FROM LIVRO L " +
               "  JOIN CATEGORIA C ON C.ID = L.CATEGORIA_ID "+
               " WHERE 1=1 ";
    }

    private String getSqlFindLivrosRecomendados() {
        return "SELECT L.ID, " +
                "      L.TITULO, " +
                "      L.AUTOR, " +
                "      L.ISBN, " +
                "      L.DATA_PUBLICACAO AS DATAPUBLICACAO, " +
                "      C.DESCRICAO AS DESCRICAOCATEGORIA, " +
                "      CASE WHEN EXISTS (SELECT E.ID " +
                "                          FROM EMPRESTIMO E " +
                "                         WHERE E.LIVRO_ID = L.ID " +
                "                           AND E.DATA_DEVOLUCAO IS NULL) " +
                "           THEN 'SIM' ELSE 'NAO' " +
                "       END AS EMPRESTADO " +
                "  FROM LIVRO L " +
                "  JOIN CATEGORIA C ON C.ID = L.CATEGORIA_ID " +
                " WHERE L.ID NOT IN (SELECT E.LIVRO_ID " +
                "                      FROM EMPRESTIMO E " +
                "                     WHERE E.USUARIO_ID = :usuarioId) " +
                "   AND L.CATEGORIA_ID IN ( " +
                "       SELECT Y.CATEGORIA_ID " +
                "         FROM (SELECT X.CATEGORIA_ID, " +
                "                      MAX(X.QTDEEMPRESTADA) AS QTDEEMPRESTADA " +
                "                 FROM (SELECT DISTINCT L.CATEGORIA_ID, " +
                "                              COUNT(L.CATEGORIA_ID) QTDEEMPRESTADA " +
                "                         FROM EMPRESTIMO E " +
                "                         JOIN LIVRO L ON L.ID = E.LIVRO_ID " +
                "                        WHERE E.USUARIO_ID = :usuarioId " +
                "                        GROUP BY L.CATEGORIA_ID " +
                "                      ) X " +
                "                GROUP BY X.CATEGORIA_ID " +
                "              ) Y ) " +
                "   AND NOT EXISTS (SELECT E.ID " +
                "                     FROM EMPRESTIMO E " +
                "                    WHERE E.LIVRO_ID = L.ID " +
                "                      AND E.DATA_DEVOLUCAO IS NULL) ";
    }

    private String getWhere(LivroParam livroParam) {
        StringBuilder sqlWhere = new StringBuilder();

        if (livroParam.getTitulo() != null) {
            sqlWhere.append(" AND UPPER(L.TITULO) LIKE :titulo ");
        }

        if (livroParam.getAutor() != null) {
            sqlWhere.append(" AND UPPER(L.AUTOR) LIKE :autor ");
        }

        if (livroParam.getIsbn() != null) {
            sqlWhere.append(" AND L.ISBN LIKE :isbn ");
        }

        if (livroParam.getDataPublicacao() != null) {
            sqlWhere.append(" AND L.DATA_PUBLICACAO = :dataPublicacao ");
        }

        if (livroParam.getDescricaoCategoria() != null) {
            sqlWhere.append(" AND UPPER(C.DESCRICAO) LIKE :descricaoCategoria ");
        }

        if (livroParam.getEmprestado() != null) {
            if (livroParam.getEmprestado()) {
                sqlWhere.append(" AND EXISTS (SELECT E.ID "+
                                "               FROM EMPRESTIMO E "+
                                "              WHERE E.LIVRO_ID = L.ID "+
                                "                AND E.DATA_DEVOLUCAO IS NULL) ");
            } else {
                sqlWhere.append(" AND NOT EXISTS (SELECT E.ID "+
                                "                   FROM EMPRESTIMO E "+
                                "                  WHERE E.LIVRO_ID = L.ID "+
                                "                    AND E.DATA_DEVOLUCAO IS NULL) ");
            }
        }

        return sqlWhere.toString();
    }

    private String getSqlProximoId() {
        return "SELECT COALESCE(MAX(L.ID), 0)+1 ID " +
               "  FROM LIVRO L ";
    }
}
