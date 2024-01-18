package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Type_Lieu {
    int id_type_lieu;
    String nom;

    public void setId_type_lieu(int id_type_lieu) {
        this.id_type_lieu = id_type_lieu;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId_type_lieu() {
        return id_type_lieu;
    }

    public String getNom() {
        return nom;
    }

    public Type_Lieu(){

    }

    public Type_Lieu(int id, String nom)
    {
        setId_type_lieu(id);
        setNom(nom);
    
    }


    public static Type_Lieu[] getAllTypeLieu(Connection connection) throws Exception{
         String query = "select * from type_lieu";
        Type_Lieu[] type_lieux;
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
                type_lieux = new Type_Lieu[0];
            }else{
                type_lieux = new Type_Lieu[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    type_lieux[i] = new Type_Lieu();
                    type_lieux[i].setId_type_lieu(resultset.getInt("id_type_lieu"));
                    type_lieux[i].setNom(resultset.getString("nom_type_lieu"));
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
        return type_lieux;
    }

    public static Type_Lieu getTypeLieuById(int id,Connection connection) throws Exception {
		String query = "select * from type_lieu where id_type_lieu=?";
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        Type_Lieu tp = new Type_Lieu();
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, id);
			statementOpen = true;
			
			
            resultset =  statement.executeQuery();
            while(resultset.next()){
                tp.setId_type_lieu(resultset.getInt("id_type_lieu"));
                tp.setNom(resultset.getString("nom_type_lieu"));
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
        return tp;
    }
}
