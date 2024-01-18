package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Voyage {
    int id_voyage;
    String nom_voyage;
    Lieu lieu;
    Bouquet bouquet;

    public void setBouquet(Bouquet bouquet) {
        this.bouquet = bouquet;
    }
    public void setId_voyage(int id_voyage) {
        this.id_voyage = id_voyage;
    }
    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }
    public void setNom_voyage(String nom_voyage) {
        this.nom_voyage = nom_voyage;
    }

    public Bouquet getBouquet() {
        return bouquet;
    }
    public int getId_voyage() {
        return id_voyage;
    }
    public Lieu getLieu() {
        return lieu;
    }
    public String getNom_voyage() {
        return nom_voyage;
    }

    public Voyage(){

    }

    public Voyage(int id_voyage,String nom_voyage,Lieu lieu, Bouquet b){
        setBouquet(b);
        setId_voyage(id_voyage);
        setLieu(lieu);
        setNom_voyage(nom_voyage);
    }

    public static void createNewVoyage(String nomVoyage,Lieu lieu,Bouquet bouquet,Connection connection) throws Exception{
        String query = "INSERT INTO \"public\".voyage\r\n" + //
                "\t( nom_voyage, id_lieu_fk, id_bouquet_fk) VALUES ( ?, ?, ? )";
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
            statement.setInt(3,bouquet.getId_bouquet());
            statement.setInt(2, lieu.getId_lieu());
            statement.setString(1, nomVoyage);
			statementOpen = true;
			
			statement.executeUpdate();
			
		
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
    }

    
    public void insertActivite(Connection connection, Activite a, Durre  d) throws Exception
    {
        String query = "INSERT INTO activite_voyage(id_voyage_fk,id_activite_fk,id_durre_fk) values(?,?,?)";

        PreparedStatement statement = null;
        boolean statementOpen = false;
        boolean closeable = false;

        try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","Fifa16");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId_voyage());
            int id_activite = a.getId_activite();
            statement.setInt(2, id_activite);
            int id_duree = d.getId();
            statement.setInt(3, id_duree);

        
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
    
    public static Voyage[] getAllVoyages(Connection connection) throws Exception{
        String query = "select * from voyage";
        Voyage[] voyages;
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
                voyages = new Voyage[0];
            }else{
                voyages = new Voyage[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyages[i] = new Voyage();
                    voyages[i].setId_voyage(resultset.getInt("id_voyage"));
                    voyages[i].setBouquet(Bouquet.getBouquetById(resultset.getInt("id_bouquet_fk"), connection));
                    voyages[i].setLieu(Lieu.getLieuById(resultset.getInt("id_lieu_fk"), connection));
                    voyages[i].setNom_voyage(resultset.getString("nom_voyage"));
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
        return voyages;
    }
      
    public static Voyage getVoyageById(int id,Connection connection) throws Exception{
        String query = "select * from voyage where id_voyage= ? ";
        Voyage voyage;
        voyage = new Voyage();
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
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyage.setId_voyage(resultset.getInt("id_voyage"));
                    voyage.setBouquet(Bouquet.getBouquetById(resultset.getInt("id_bouquet_fk"), connection));
                    voyage.setLieu(Lieu.getLieuById(resultset.getInt("id_lieu_fk"), connection));
                    voyage.setNom_voyage(resultset.getString("nom_voyage"));
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
        return voyage;
    }

    public  int countPrixVoyage(int duree,Connection connection) throws Exception {
		String query = "select * from prix_voyage_durre where id_voyage_fk=? AND id_durre_fk=?";
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        int count =0;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId_voyage());
            statement.setInt(2, duree);
			statementOpen = true;
			
			
            resultset =  statement.executeQuery();
            
            while(resultset.next()){
                count++;

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
        return count;
    }



    public  void updatePrixVoyage(int duree,double prix, Connection connection) throws Exception{
        String query = "update prix_voyage_durre set prix=? where id_voyage_fk = ? AND id_durre_fk=?";
        PreparedStatement statement = null;
		boolean statementOpen = false;
		boolean closeable = false;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setDouble(1, prix);
            statement.setInt(2, this.getId_voyage());
            statement.setInt(3, duree);
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

    public void insertPrixVoyage(int duree, double prix,Connection connection) throws Exception{
        if(this.countPrixVoyage(duree, connection)!=0)
        {
            this.updatePrixVoyage(duree, prix, connection);
        }
        else 
        {
            String query = "INSERT INTO prix_voyage_durre ( id_voyage_fk, id_durre_fk, prix) VALUES ( ?, ?, ? );";
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
                statement.setInt(1, this.getId_voyage());
                statement.setInt(2, duree);
                statement.setDouble(3, prix);
    
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



    public void sortieVoyageAction(Connection connection) throws Exception{
        
    }


}
