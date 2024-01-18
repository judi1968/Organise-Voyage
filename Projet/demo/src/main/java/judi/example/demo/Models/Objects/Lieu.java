package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;


public class Lieu {
    int id_lieu;
    String nom;
    Type_Lieu type;

    public void setId_Lieu(int id_lieu) {
        this.id_lieu = id_lieu;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setType(Type_Lieu type) {
        this.type = type;
    }


    public String getNom() {
        return nom;
    }
    public int getId_lieu() {
        return id_lieu;
    }

    public Type_Lieu getType() {
        return type;
    }


    public Lieu(){

    }

    public Lieu(int id, String nom, Type_Lieu type)
    {
        setId_Lieu(id);
        setNom(nom);
        setType(type);
    
    }


    public static Lieu[] getAllLieu(Connection connection) throws Exception{
         String query = "select * from lieu";
        Lieu[] Lieux;
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
                Lieux = new Lieu[0];
            }else{
                Lieux = new Lieu[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    Lieux[i] = new Lieu();
                    Lieux[i].setId_Lieu(resultset.getInt("id_lieu"));
                    Lieux[i].setNom(resultset.getString("nom_Lieu"));
                    Lieux[i].setType(Type_Lieu.getTypeLieuById(resultset.getInt("id_type_lieu_fk"), connection));


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
        return Lieux;
    }

    public static Lieu getLieuById(int id,Connection connection) throws Exception {
		String query = "select * from lieu where id_lieu=?";
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        Lieu l = new Lieu();
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
                l.setId_Lieu(resultset.getInt("id_lieu"));
                l.setNom(resultset.getString("nom_lieu"));
                l.setType(Type_Lieu.getTypeLieuById(resultset.getInt("id_type_lieu_fk"), connection));

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
        return l;
    }
}
