package fr.eni.papeterie.bll;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.DAOFactory;

import java.util.List;

public class CatalogueManager {
    private static CatalogueManager instance;
    private ArticleDAO daoArticle;

    private CatalogueManager() {
        this.daoArticle = DAOFactory.getArticleDAO(); // daoArticle représente la DAL
    }

    private void validerArticle(Article a) throws BLLException {
        if (a.getDesignation() == null || a.getDesignation().equalsIgnoreCase("")) {
            throw new BLLException("Erreur de désignation.");
        }
        if (a.getMarque() == null || a.getMarque().equalsIgnoreCase("")) {
            throw new BLLException("Erreur de marque.");
        }
        if (a.getReference() == null || a.getReference().equalsIgnoreCase("")) {
            throw new BLLException("Erreur de référence.");
        }
        if (a.getQteStock() < 0) {
            throw new BLLException("Erreur de stock.");
        }
        if (a.getPrixUnitaire() < 0f) {
            throw new BLLException("Erreur de prix unitaire.");
        }
        if (a instanceof Ramette) {
            if (((Ramette) a).getGrammage() <= 0) {
                throw new BLLException("Erreur de grammage.");
            }
        }
        if (a instanceof Stylo) {
            if (((Stylo) a).getCouleur() == null) {
                throw new BLLException("Erreur de couleur.");
            }
        }
    }

    public List<Article> getCatalogue() throws BLLException {
        List<Article> catalogue = null;
        try {
            catalogue = daoArticle.selectAll();
        } catch (DALException e) {
            throw new BLLException("Erreur dans la methode getCatalogue.");
        }
        return catalogue;
    }

    public void addArticle(Article a) throws BLLException {
        try {
            this.validerArticle(a);
            daoArticle.insert(a);
        } catch (DALException e) {
            throw new BLLException("Erreur dans la méthode addArticle.");
        }
    }

    public void removeArticle(int index) throws BLLException {
        try {
            daoArticle.delete(index);
        } catch (DALException e) {
            throw new BLLException("Erreur dans la méthode removeArticle.");
        }
    }

    public Article getArticle(int index) throws BLLException {
        Article article;
        try {
            article = daoArticle.selectById(index);
        } catch (DALException e) {
            throw new BLLException("Erreur dans la méthode getArticle.");
        }
        return article;
    }

    public void updateArticle(Article a) throws BLLException {
        try {
            this.validerArticle(a);
            daoArticle.update(a);
        } catch (DALException e) {
            throw new BLLException("Erreur dans la méthode updateArticle.");
        }
    }

    public static CatalogueManager getInstance() {
        if (instance == null) {
            instance = new CatalogueManager();
        }
        return instance;
    }
}
