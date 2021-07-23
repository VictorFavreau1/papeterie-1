package fr.eni.papeterie.dal;

import java.util.Properties;

/**
 * Settings
 *
 * Permet la récupération d'une valeur du fichier jdbc.properties
 * par la méthode getPropriete()
 */
public class Settings {

    private static Properties propriete;

    // Je lis le fichier jdbc.properties
    static {
        try {
            propriete = new Properties();
            propriete.load(Settings.class.getResourceAsStream("jdbc.properties"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // Je récupère une des propriétés
    public static String getPropriete(String cle) {
        return propriete.getProperty(cle, null);
    }
}
