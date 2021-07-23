package fr.eni.papeterie.dal;

import fr.eni.papeterie.dal.jdbc.ArticleDAOJdbcImpl;

/**
 * Usine
 *
 * Permet la création d'une instance de ArticleDAOJdbcImpl
 * par la méthode getArticleDAO()
 */
public class DAOFactory {
    public static ArticleDAO getArticleDAO() { // static pour ne pas avoir a créer d'instance pour l'utiliser
        ArticleDAO vDAO = new ArticleDAOJdbcImpl();
        return vDAO;
    }
}

// DAOFactory.getArticleDAO(); car static
