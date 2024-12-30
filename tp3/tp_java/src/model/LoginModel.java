package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.LoginDAOimpl;

public class LoginModel {

    private LoginDAOimpl loginDAO;

    public LoginModel(LoginDAOimpl loginDAO) {
        this.loginDAO = loginDAO;
    }

    // Authentication method
    public boolean authenticate(String username, String password) {
        return loginDAO.authenticate(username, password); // Delegate to DAO
    }
    public String getUserRole(String username) {
        // Implémentation pour récupérer le rôle de l'utilisateur depuis la base de données
        // Par exemple, on pourrait faire une requête SQL pour obtenir le rôle en fonction du nom d'utilisateur
        String role = "employee"; // Valeur par défaut
        String query = "SELECT role FROM users WHERE username = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion", "user", "password");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                role = rs.getString("role"); // Récupérer le rôle depuis la base de données
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return role;
    }

}
