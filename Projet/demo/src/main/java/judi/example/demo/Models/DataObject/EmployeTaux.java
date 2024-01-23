package judi.example.demo.Models.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Niveau_employe;
import judi.example.demo.Models.Utils.DateHeure;

public class EmployeTaux {
    String nomEmploye;
    String fonction;
    String niveau;
    String date;
    Double taux;

    public String getDate() {
        return date;
    }

    public String getFonction() {
        return fonction;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getNomEmploye() {
        return nomEmploye;
    }

    public Double getTaux() {
        return taux;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public EmployeTaux()
    {

    }

    public static EmployeTaux[] getAllEmployeTaux(DateHeure dateDonne,Connection connection) throws Exception{
        System.out.println(dateDonne.getDateTimeString());
        String query = "select *,'"+dateDonne.getDateTimeString()+"'-date_embauche nombre_jours from employe where date_embauche< ?";
        EmployeTaux[] employeTaux;
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(dateDonne.getDate(), dateDonne.getHeure())));

			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                employeTaux = new EmployeTaux[0];
            }else{
                employeTaux = new EmployeTaux[size];
                int i = 0;
                resultset =  statement.executeQuery();
                String nom = "";
                while(resultset.next()){
                    employeTaux[i] = new EmployeTaux();
                    employeTaux[i].setNomEmploye(resultset.getString("fonction_designation").split("-")[0]);
                    employeTaux[i].setDate(resultset.getString("date_embauche"));
                    employeTaux[i].setFonction(resultset.getString("fonction_designation").split("-")[1]);
                    employeTaux[i].setNiveau(Niveau_employe.getNiveauByJourString(resultset.getString("nombre_jours")));
                    employeTaux[i].setTaux(resultset.getDouble("prix")*Niveau_employe.getTauxByJourString(resultset.getString("nombre_jours")));
                    System.out.println(employeTaux[i].getNomEmploye());
                    i++;
                }
            }
			statement.close();
			
		}catch (Exception e) {
			throw e;
		}finally {
			if(statementOpen) {
				statement.close();
			}
			if(resultsetOpen) {
				resultset.close();
			}
			if(closeable) {
				connection.commit();
				connection.close();
			}
		}
        return employeTaux;
    }
}
