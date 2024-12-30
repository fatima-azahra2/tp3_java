package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Employe;


public class ImportData implements DataImportExport<Employe> {
    private Connection conn;

 
    @Override
    public void importData(String filePath) {
        String query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement ps = conn.prepareStatement(query)) {

            String line = reader.readLine(); // Skip the header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    ps.setString(1, data[0].trim()); // nom
                    ps.setString(2, data[1].trim()); // prenom
                    ps.setString(3, data[2].trim()); // email
                    ps.setString(4, data[3].trim()); // telephone
                    ps.setString(5, data[4].trim()); // salaire
                    ps.setString(6, data[5].trim()); // role
                    ps.setString(7, data[6].trim()); // poste
                    ps.addBatch();
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            ps.executeBatch();
            System.out.println("Employés importés avec succès.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void exportData(String fileName, List<Employe> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("nom,prenom,email,telephone,role,poste,salaire");
            writer.newLine();
            for (Employe employee : data) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%.2f",
                        employee.getNom(),
                        employee.getPrenom(),
                        employee.getEmail(),
                        employee.getTelephone(),
                        employee.getRole(),
                        employee.getPost(),
                        employee.getSalaire());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Données exportées avec succès.");
        }
    }
}