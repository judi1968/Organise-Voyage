package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Vector;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.DatabaseConnection.Query;

public class Bouquet {
    int id_bouquet;
    String nom_bouquet;
    public void setId_bouquet(int id_bouquet) {
        this.id_bouquet = id_bouquet;
    }
    public void setNom_bouquet(String nom_bouquet) {
        this.nom_bouquet = nom_bouquet;
    }
    public int getId_bouquet() {
        return id_bouquet;
    }
    public String getNom_bouquet() {
        return nom_bouquet;
    }
    public static void insert_bouquet(String nom_bouquet){
        Query query = new Query();
        nom_bouquet = query.makeNewString(nom_bouquet);
        query.execute("insert","insert into Bouquet(nom_bouquet) values("+ nom_bouquet +")");
    }
    public static Bouquet[] getAllBouquet(Connection connection) throws Exception{
        String query = "select * from bouquet";
        Bouquet[] bouquets;
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
                bouquets = new Bouquet[0];
            }else{
                bouquets = new Bouquet[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    bouquets[i] = new Bouquet();
                    bouquets[i].setId_bouquet(resultset.getInt("id_bouquet"));
                    bouquets[i].setNom_bouquet(resultset.getString("nom_bouquet"));
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
        return bouquets;
    }

    public static Bouquet getBouquetById(int id_bouquet,Connection connection) throws Exception{
        String query = "select * from bouquet where id_bouquet=?";
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        Bouquet bouquet = new Bouquet();
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, id_bouquet);
			statementOpen = true;
			
			
            resultset =  statement.executeQuery();
            while(resultset.next()){
                bouquet.setId_bouquet(resultset.getInt("id_bouquet"));
                bouquet.setNom_bouquet(resultset.getString("nom_bouquet"));
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
        return bouquet;
    }

    public void addActivite(Activite activite,Connection connection) throws Exception{
        String query = "INSERT INTO \"public\".activite_bouquet\r\n" + //
                "\t( id_activite_fk, id_bouquet_fk) VALUES ( ?, ? );";
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
            statement.setInt(1, activite.getId_activite());
            statement.setInt(2, this.getId_bouquet());
			statementOpen = true;
			
			statement.executeUpdate();
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
    }
}