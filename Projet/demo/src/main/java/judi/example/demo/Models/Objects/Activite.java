package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.DatabaseConnection.Query;

public class Activite{
    String nom_activite;
    int id_activite;
    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }
    public void setNom_activite(String nom_activite) {
        this.nom_activite = nom_activite;
    }
    public int getId_activite() {
        return id_activite;
    }
    public String getNom_activite() {
        return nom_activite;
    }
    public static void insert_activite(String activite){
        Query query = new Query();
        activite = query.makeNewString(activite);
        query.execute("insert","insert into Activite(nom_activite) values("+ activite +")");
    }
    
    public static Activite[] getAllActiviteWithoutBouquet(Bouquet bouquet,Connection connection) throws Exception{
        String query = "select * from activite where id_activite not in (select id_activite from v_activite_bouquet where id_bouquet=?)";
        Activite[] activites;
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
            statement.setInt(1,bouquet.getId_bouquet());
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                activites = new Activite[0];
            }else{
                activites = new Activite[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    activites[i] = new Activite();
                    activites[i].setId_activite(resultset.getInt("id_activite"));
                    activites[i].setNom_activite(resultset.getString("nom_activite"));
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
        return activites;
    }

    public static Activite[] getAllActiviteWithBouquet(Bouquet bouquet,Connection connection) throws Exception{
        String query = "select * from activite where id_activite in (select id_activite from v_activite_bouquet where id_bouquet=?)";
        Activite[] activites;
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
            statement.setInt(1,bouquet.getId_bouquet());
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                activites = new Activite[0];
            }else{
                activites = new Activite[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    activites[i] = new Activite();
                    activites[i].setId_activite(resultset.getInt("id_activite"));
                    activites[i].setNom_activite(resultset.getString("nom_activite"));
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
        return activites;
    }
    public static Activite[] getAllActivite(Connection connection) throws Exception{
        String query = "select * from activite";
        Activite[] activites;
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
                activites = new Activite[0];
            }else{
                activites = new Activite[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    activites[i] = new Activite();
                    activites[i].setId_activite(resultset.getInt("id_activite"));
                    activites[i].setNom_activite(resultset.getString("nom_activite"));
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
        return activites;
    }
    
	public static Activite getActiviteById(int id,Connection connection) throws Exception{
        String query = "select * from activite where id_activite = ?";
        Activite activite = new Activite();
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
			
			resultset =  statement.executeQuery();
			
            while(resultset.next()){
                    activite.setId_activite(resultset.getInt("id_activite"));
                    activite.setNom_activite(resultset.getString("nom_activite"));
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
        return activite;
    }
    public boolean isInBouquet(Bouquet bouquet,Connection connection) throws Exception{
        String query = "select * from activite where id_activite in (select id_activite from v_activite_bouquet where id_bouquet=? and id_activite=?)";
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        boolean isExist = false;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1,bouquet.getId_bouquet());
            statement.setInt(2, this.getId_activite());
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
		
            while(resultset.next()){
                isExist = true;
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
        return isExist;
    }

	public static void updateActivitePrixNotActif(int id,Connection connection) throws Exception{
        String query = "update activite_prix set etat=5 where id_activite_fk = ?";
        PreparedStatement statement = null;
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
			
			statement.executeUpdate();
			
            
			statement.close();
			
		}catch (Exception e) {
			throw e;
		}finally {
			if(statementOpen) {
				statement.close();
			}
			if(closeable) {
				connection.commit();
				connection.close();
			}
		}
    }

    public void addNewActivitePrix(double prix,Connection connection) throws Exception{
        updateActivitePrixNotActif(this.getId_activite(), connection);
        String query = "INSERT INTO activite_prix( id_activite_fk, prix, etat) VALUES ( ?, ?,? );";
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
            statement.setInt(1, this.getId_activite());
            statement.setDouble(2, prix);
            statement.setInt(3,10);
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

	public void addActiviteToStock(int quantite,Connection connection) throws Exception{
        String query = "INSERT INTO stock_billet_activite( id_activite_fk, nb_entrant,nb_sortant) VALUES ( ?, ? ,0)";
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
            statement.setInt(1, this.getId_activite());
			statement.setInt(2, quantite);

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

	public void addActiviteSortant(int quantite,Connection connection) throws Exception{
        String query = "INSERT INTO stock_billet_activite( id_activite_fk, nb_entrant,nb_sortant) VALUES ( ?, 0 ,? )";
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
            statement.setInt(1, this.getId_activite());
			statement.setInt(2, quantite);

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
	
	public int getNombreStockById(Connection connection) throws Exception{
        String query = "select * from v_stock_billet_activite_reste where id_activite_fk=?";
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
            statement.setInt(1, this.getId_activite());

			statementOpen = true;
			
			resultset = statement.executeQuery();
			while (resultset.next()) {
				size = resultset.getInt("nb_reste");
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
		return size;
    }
}
