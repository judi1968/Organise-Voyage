package judi.example.demo.Models.Objects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Niveau_employe {
    int id_niveau_employe;
    String designation_niveau;
    int nombre_jour_minimale;
    int nombre_jour_maximale;
    double effet_salaire;

    public void setEffet_salaire(double effet_salaire) {
        this.effet_salaire = effet_salaire;
    }
    public double getEffet_salaire() {
        return effet_salaire;
    }

    public void setDesignation_niveau(String designation_niveau) {
        this.designation_niveau = designation_niveau;
    }
    public void setId_niveau_employe(int id_niveau_employe) {
        this.id_niveau_employe = id_niveau_employe;
    }
    public void setNombre_jour_maximale(int nombre_jour_maximale) {
        this.nombre_jour_maximale = nombre_jour_maximale;
    }
    public void setNombre_jour_minimale(int nombre_jour_minimale) {
        this.nombre_jour_minimale = nombre_jour_minimale;
    }
    public String getDesignation_niveau() {
        return designation_niveau;
    }
    public int getId_niveau_employe() {
        return id_niveau_employe;
    }
    public int getNombre_jour_maximale() {
        return nombre_jour_maximale;
    }
    public int getNombre_jour_minimale() {
        return nombre_jour_minimale;
    }
    
        public static Niveau_employe[] getAllNiveau_employes(Connection connection) throws Exception{
        String query = "select * from niveau_employe";
        Niveau_employe[] niveaux;
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

			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                niveaux = new Niveau_employe[0];
            }else{
                niveaux = new Niveau_employe[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    niveaux[i] = new Niveau_employe();
                    niveaux[i].setId_niveau_employe(resultset.getInt("id_niveau_employe"));
                    niveaux[i].setDesignation_niveau(resultset.getString("designation_niveau"));
                    niveaux[i].setNombre_jour_maximale(resultset.getInt("nombre_jour_maximale"));
                    niveaux[i].setNombre_jour_minimale(resultset.getInt("nombre_jour_minimale"));
                    niveaux[i].setEffet_salaire(resultset.getDouble("effet_salaire"));

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
        return niveaux;
    }
    public static String getNiveauByJourString(String date) throws Exception{
        System.out.println(date);
        String date2 = date.split(" ")[0];
        int date3 = Integer.parseInt(date2);
        return  Niveau_employe.getNiveau_employeByDay(date3, null).getDesignation_niveau();
    }
    public static double getTauxByJourString(String date) throws Exception{
        System.out.println(date);
        String date2 = date.split(" ")[0];
        int date3 = Integer.parseInt(date2);
        return  Niveau_employe.getNiveau_employeByDay(date3, null).getEffet_salaire();
    }
    public static Niveau_employe getNiveau_employeByDay(int nombre_jour, Connection connection) throws Exception{
        String query = "select * from niveau_employe where nombre_jour_minimale<? AND nombre_jour_maximale>=?";
        Niveau_employe niveau = new Niveau_employe();
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
            statement.setInt(1, nombre_jour);
            statement.setInt(2, nombre_jour);

			statementOpen = true;
		
			
            resultset =  statement.executeQuery();
            while(resultset.next()){
                niveau.setId_niveau_employe(resultset.getInt("id_niveau_employe"));
                niveau.setDesignation_niveau(resultset.getString("designation_niveau"));
                niveau.setNombre_jour_maximale(resultset.getInt("nombre_jour_maximale"));
                niveau.setNombre_jour_minimale(resultset.getInt("nombre_jour_minimale"));
                niveau.setEffet_salaire(resultset.getDouble("effet_salaire"));
                System.out.println(niveau.getDesignation_niveau());
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
        return niveau;
    }
}



