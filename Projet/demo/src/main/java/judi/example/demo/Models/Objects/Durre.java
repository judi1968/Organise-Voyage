package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Durre {
    int id;
    String nom;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Durre(){}
    public Durre(int id, String nom){
        setId(id);
        setNom(nom);
    }

    public static Durre[] getAllDurres(Connection connection) throws Exception{
        String query = "select * from durre";
        Durre[] durres;
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
                durres = new Durre[0];
            }else{
                durres = new Durre[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    durres[i] = new Durre(resultset.getInt("id_duree"),resultset.getString("nom_duree"));
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
        return durres;
    }

    public static Durre getDurreById(int id , Connection connection) throws Exception{
        String query = "select * from durre where id_duree = ? ";
        Durre durre = new Durre();
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
            statement.setInt(1, id);
			statementOpen = true;
			
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    durre = null;
                    durre = new Durre(resultset.getInt("id_duree"),resultset.getString("nom_duree"));
                    i++;
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
        return durre;
    }
}
