package fr.eni.papeterie.bo;

public class Stylo extends Article {
    private String couleur;

    public Stylo() {
    }

    public Stylo(Integer idArticle, String marque, String reference, String designation, float prixUnitaire, int qteStock, String couleur) {
        super(idArticle, marque, reference, designation, prixUnitaire, qteStock);
        this.couleur = couleur;
    }

    public Stylo(String marque, String reference, String designation, float prixUnitaire, int qteStock, String couleur) {
        super(marque, reference, designation, prixUnitaire, qteStock);
        this.couleur = couleur;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Stylo{" +
                "couleur='" + couleur + '\'' +
                '}';
    }
}
