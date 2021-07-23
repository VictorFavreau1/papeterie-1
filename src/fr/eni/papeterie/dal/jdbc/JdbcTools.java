package fr.eni.papeterie.dal.jdbc;

import fr.eni.papeterie.dal.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTools {
    private static String url;
    private static String login;
    private static String mdp;

    static {
        url = Settings.getPropriete("url");
        login = Settings.getPropriete("login");
        mdp = Settings.getPropriete("mdp");
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                url, login, mdp
        );
        return connection;
    }
}
