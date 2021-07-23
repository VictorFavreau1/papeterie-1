package fr.eni.papeterie.dal;

import fr.eni.papeterie.bo.Article;

import java.util.List;

/**
 * Interface JDBC
 */
public interface ArticleDAO {
    void insert(Article article) throws DALException;

    Article selectById(int id) throws DALException;

    void delete(int id) throws DALException;

    List<Article> selectAll() throws DALException;

    void update(Article article) throws DALException;

    List<Article> selectByMarque(String marque) throws DALException;

    List<Article> selectByMotCle(String motCle) throws DALException;

}
