package com.deployrest.rest_prova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connessione_Sql {

    private static final String URL = "jdbc:mysql://localhost:3306/rubrica";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection ottieniConnessione() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connessione stabilita con successo!");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Errore di connessione: " + e.getMessage());
            e.printStackTrace();
        }
        
        return con;
    }
}