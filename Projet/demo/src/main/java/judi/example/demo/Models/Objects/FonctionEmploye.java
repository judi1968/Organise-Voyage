package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class FonctionEmploye {
    private int id_fonction;
    private String nom_designation;

    public int getId_fonction() {
        return id_fonction;
    }
    public String getNom_designation() {
        return nom_designation;
    }
    public void setId_fonction(int id_fonction) {
        this.id_fonction = id_fonction;
    }
    public void setNom_designation(String nom_designation) {
        this.nom_designation = nom_designation;
    }

    public FonctionEmploye(){}

    public FonctionEmploye(int id,String nom){
        setId_fonction(id);
        setNom_designation(nom);
    }
    // Méthode pour insérer une nouvelle fonction employé
    public void insertNewFonctionEmploye(Connection connection) throws Exception {
        String query = "INSERT INTO fonction_employe (nom_designation) VALUES (?);";
        PreparedStatement statement = null;
        boolean statementOpen = false;
        boolean closeable = false;

        try {
            if (connection == null) {
                connection = ConnectionPostgres.connect("localhost", 5432, "voyage", "postgres", "mdpprom15");
                connection.setAutoCommit(false);
                closeable = true;
            }

            statement = connection.prepareStatement(query);
            statement.setString(1, this.getNom_designation());

            statementOpen = true;
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statementOpen) {
                statement.close();
            }
            if (closeable) {
                connection.commit();
                connection.close();
            }
        }
    }

    // Méthode pour obtenir toutes les fonctions employé
    public static FonctionEmploye[] getAllFonctionEmploye(Connection connection) throws Exception {
        String query = "SELECT * FROM fonction_employe";
        FonctionEmploye[] fonctionEmployes;
        int size = 0;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean statementOpen = false;
        boolean closeable = false;

        try {
            if (connection == null) {
                connection = ConnectionPostgres.connect("localhost", 5432, "voyage", "postgres", "mdpprom15");
                connection.setAutoCommit(false);
                closeable = true;
            }

            statement = connection.prepareStatement(query);
            statementOpen = true;
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                size++;
            }

            if (size == 0) {
                fonctionEmployes = new FonctionEmploye[0];
            } else {
                fonctionEmployes = new FonctionEmploye[size];
                int i = 0;
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    fonctionEmployes[i] = new FonctionEmploye();
                    fonctionEmployes[i].setId_fonction(resultSet.getInt("id_fonction"));
                    fonctionEmployes[i].setNom_designation(resultSet.getString("nom_designation"));

                    i++;
                }
            }
            statement.close();

        } catch (Exception e) {
            throw e;
        } finally {
            if (statementOpen) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (closeable) {
                connection.commit();
                connection.close();
            }
        }
        return fonctionEmployes;
    }

    // Méthode pour obtenir une fonction employé par ID
    public static FonctionEmploye getFonctionEmployeById(int id, Connection connection) throws Exception {
        String query = "SELECT * FROM fonction_employe WHERE id_fonction=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean statementOpen = false;
        boolean closeable = false;
        FonctionEmploye fonctionEmploye = new FonctionEmploye();

        try {
            if (connection == null) {
                connection = ConnectionPostgres.connect("localhost", 5432, "voyage", "postgres", "mdpprom15");
                connection.setAutoCommit(false);
                closeable = true;
            }

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statementOpen = true;

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                fonctionEmploye.setId_fonction(resultSet.getInt("id_fonction"));
                fonctionEmploye.setNom_designation(resultSet.getString("nom_designation"));
            }

            statement.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statementOpen) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (closeable) {
                connection.commit();
                connection.close();
            }
        }
        return fonctionEmploye;
    }
}

