package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ArticleDAOJdbcImpl
 *
 * Implémentation de l'interface ArticleDAO
 */
public class ArticleDAOJdbcImpl implements ArticleDAO {

    final String SQL_DELETE = "DELETE FROM Articles WHERE idArticle=?;";
    final String SQL_SELECT_ALL = "SELECT idArticle, reference, marque, designation, prixUnitaire, qteStock, grammage, couleur, type FROM Articles;";
    final String SQL_UPDATE = "UPDATE Articles " +
            "set reference=?, marque=?, designation=?, prixUnitaire=?, qteStock=?, grammage=?, couleur=?, type=? " +
            "WHERE idArticle=?;";
    final String SQL_INSERT = "insert into Articles(reference,marque,designation,prixUnitaire,qteStock,grammage,couleur, type) "
            + " values(?,?,?,?,?,?,?,?)";
    final String SQL_SELECT_BY_ID = "SELECT idArticle, reference, marque, designation," +
            " prixUnitaire, qteStock, grammage, couleur, type" +
            " FROM Articles WHERE idArticle=?;";
    final String SQL_SELECT_BY_MARQUE = "SELECT idArticle, reference, marque, designation," +
            " prixUnitaire, qteStock, grammage, couleur, type" +
            " FROM Articles WHERE marque=?;";
    final String SQL_SELECT_BY_MOTCLE = "SELECT idArticle, reference, marque, designation," +
            " prixUnitaire, qteStock, grammage, couleur, type" +
            " FROM Articles WHERE marque LIKE ? OR designation LIKE ?;";

    @Override
    public void insert(Article article) throws DALException {
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement etatPrepare = connection.prepareStatement(
                    this.SQL_INSERT, Statement.RETURN_GENERATED_KEYS
            );
            etatPrepare.setString(1, article.getReference());
            etatPrepare.setString(2, article.getMarque());
            etatPrepare.setString(3, article.getDesignation());
            etatPrepare.setFloat(4, article.getPrixUnitaire());
            etatPrepare.setInt(5, article.getQteStock());
            if (article instanceof Stylo) {
                etatPrepare.setNull(6, Types.INTEGER);
                etatPrepare.setString(7, ((Stylo) article).getCouleur());
                etatPrepare.setString(8, "STYLO");
            } else {
                etatPrepare.setInt(6, ((Ramette) article).getGrammage());
                etatPrepare.setNull(7, Types.VARCHAR);
                etatPrepare.setString(8, "RAMETTE");
            }
            etatPrepare.executeUpdate();
            ResultSet clesGenerees = etatPrepare.getGeneratedKeys(); // Récupérer les colonnes auto incrémentée
            if (clesGenerees.next()) {
                int idGenere = clesGenerees.getInt(1);
                article.setIdArticle(idGenere);
            }
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode insert.");
        }
    }

    /**
     * @param id de l'article recherché
     * @return un article
     */
    @Override
    public Article selectById(int id) throws DALException {
        Article article = null;
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement ep = connection.prepareStatement(this.SQL_SELECT_BY_ID);
            ep.setInt(1, id); // Je définis la valeur du premier point d'interrogation
            ResultSet rs = ep.executeQuery();
            while (rs.next()) { // Pour chaque ligne du ResultSet
                int identifiant = rs.getInt("idArticle");
                String reference = rs.getString("reference");
                String marque = rs.getString("marque");
                String designation = rs.getString("designation");
                float prixUnitaire = rs.getFloat("prixUnitaire");
                int quantiteStock = rs.getInt("qteStock");
                int grammage = rs.getInt("grammage");
                String couleur = rs.getString("couleur");
                String type = rs.getString("type");
                if (type.equalsIgnoreCase("STYLO")) {
                    article = new Stylo(identifiant, reference, marque, designation, prixUnitaire, quantiteStock, couleur);
                } else {
                    article = new Ramette(identifiant, reference, marque, designation, prixUnitaire, quantiteStock, grammage);
                }
            }
        } catch (Exception e) {
            throw new DALException("Erreur dans la méthode selectById.");
        }
        return article;
    }

    @Override
    public void delete(int id) throws DALException {
        try (Connection connection = JdbcTools.getConnection()) {
            // Etat preparé
            PreparedStatement reqDelete = connection.prepareStatement(this.SQL_DELETE);
            // Points d'interrogations
            reqDelete.setInt(1, id);
            // ExecuteUpdate
            reqDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode delete.");
        }
    }

    @Override
    public List<Article> selectAll() throws DALException {
        List<Article> articles = new ArrayList<>();
        Article article = null;
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement etatPrepare = connection.prepareStatement(this.SQL_SELECT_ALL);
            ResultSet rs = etatPrepare.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").trim().equalsIgnoreCase("STYLO")) {
                    article = new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")
                    );
                } else {
                    article = new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")
                    );
                }
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode selectAll.");
        }
        return articles;
    }

    @Override
    public void update(Article article) throws DALException {
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement etatPrepare = connection.prepareStatement(this.SQL_UPDATE);
            etatPrepare.setString(1, article.getReference());
            etatPrepare.setFloat(4, article.getPrixUnitaire());
            etatPrepare.setString(2, article.getMarque());
            etatPrepare.setString(3, article.getDesignation());
            etatPrepare.setInt(5, article.getQteStock());
            etatPrepare.setInt(9, article.getIdArticle());
            if (article instanceof Stylo) {
                etatPrepare.setString(8, "STYLO");
                etatPrepare.setString(7, ((Stylo) article).getCouleur());
                etatPrepare.setNull(6, Types.VARCHAR);
            } else {
                etatPrepare.setString(8, "RAMETTE");
                etatPrepare.setNull(7, Types.INTEGER);
                etatPrepare.setInt(6, ((Ramette) article).getGrammage());
            }
            etatPrepare.executeUpdate();
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode update.");
        }
    }

    @Override
    public List<Article> selectByMarque(String marque) throws DALException {
        List<Article> articles = new ArrayList<>();
        Article article = null;
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement etatPrepare = connection.prepareStatement(this.SQL_SELECT_BY_MARQUE);
            etatPrepare.setString(1, marque);
            ResultSet rs = etatPrepare.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").trim().equalsIgnoreCase("STYLO")) {
                    article = new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")
                    );
                } else {
                    article = new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")
                    );
                }
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode selectByMarque.");
        }
        return articles;
    }

    @Override
    public List<Article> selectByMotCle(String motCle) throws DALException {
        List<Article> articles = new ArrayList<>();
        Article article = null;
        try (Connection connection = JdbcTools.getConnection()) {
            PreparedStatement etatPrepare = connection.prepareStatement(this.SQL_SELECT_BY_MOTCLE);
            etatPrepare.setString(1, motCle);
            etatPrepare.setString(2, motCle);
            ResultSet rs = etatPrepare.executeQuery();
            while (rs.next()) {
                if (rs.getString("type").trim().equalsIgnoreCase("STYLO")) {
                    article = new Stylo(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getString("couleur")
                    );
                } else {
                    article = new Ramette(
                            rs.getInt("idArticle"),
                            rs.getString("reference"),
                            rs.getString("marque"),
                            rs.getString("designation"),
                            rs.getFloat("prixUnitaire"),
                            rs.getInt("qteStock"),
                            rs.getInt("grammage")
                    );
                }
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new DALException("Erreur dans la méthode selectByMotCle.");
        }
        return articles;
    }

}
