package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Client {
    int id_client;
    String nom;
    String prenom;
    Genre genre;
    public void setGenre(String id_genre)throws Exception{
        if (id_genre.trim().length()<0) {
         throw new Exception("Id genre Invalide");   
        }
        int id = Integer.parseInt(id_genre);
        setGenre(id);
    }
    public void setGenre(int id_genre) throws Exception{
        if (id_genre<0) {throw new Exception("Id genre invalide");}
        setGenre(Genre.getGenreById(id_genre, null));
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setNom(String nom)throws Exception {
        if(nom.trim().length()==0)
        {
            throw new Exception("nom non valide");
        }
        else 
        {
            this.nom = nom;

        }
    }

    public void setPrenom(String prenom)throws Exception {
        if(prenom.trim().length()==0)
        {
            throw new Exception("prenom non valide");
        }
        else 
        {
            this.prenom = prenom;

        }
    }
    public Genre getGenre() {
        return genre;
    }

    public int getId_client() {
        return id_client;
    }

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }


    public static Client[] getAllClient(Connection connection) throws Exception{
        String query = "select * from client";
        Client[] clients;
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
                clients = new Client[0];
            }else{
                clients = new Client[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    clients[i] = new Client();
                    clients[i].setId_client(resultset.getInt("id_client"));
                    clients[i].setNom(resultset.getString("nom"));
                    clients[i].setPrenom(resultset.getString("prenom"));
                    clients[i].setGenre(Genre.getGenreById(resultset.getInt("id_genre_fk"),connection));


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
        return clients;
    }

    public static Client getClientById(int id,Connection connection) throws Exception{
        String query = "select * from client where id_client= ? ";
        Client client;
        client = new Client();
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
                    client.setId_client(resultset.getInt("id_client"));
                    client.setNom(resultset.getString("nom"));
                    client.setPrenom(resultset.getString("prenom"));
                    client.setGenre(Genre.getGenreById(resultset.getInt("id_genre_fk"),connection));
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
        return client;
    }
    
    public void modifyStock(Connection connection) throws Exception{
        System.out.println("ici");
        String query = "select * from v_nombre_billet_activite_finale_client_panier where id_client_fk= ? ";
        Client client;
        client = new Client();
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        boolean stockSuffisant = true;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId_client());
			statementOpen = true;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    System.out.println("ici");
                   Activite activite = new Activite();
                   activite.setId_activite(resultset.getInt("id_activite_fk"));
                    activite.addActiviteSortant(resultset.getInt("somme_nombre_activite"), connection); 
                      
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

    public boolean verifyStockActiviteSuffisantByIdClient(Connection connection) throws Exception{
        String query = "select * from v_nombre_billet_activite_finale_client_panier where id_client_fk= ? ";
        Client client;
        client = new Client();
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        boolean stockSuffisant = true;
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId_client());
			statementOpen = true;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    if (resultset.getInt("nb_reste_finale")<0) {
                        stockSuffisant=false;
                        break;
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
        return stockSuffisant;
    }

    public void insertNewClient(Connection connection) throws Exception
    {
        String query = "INSERT INTO client(nom,prenom,id_genre_fk) values(?,?,?)";

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
            statement.setString(1, this.getNom());
            statement.setString(2, this.getPrenom());
            statement.setInt(3, this.getGenre().getId_genre());
            
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


}
