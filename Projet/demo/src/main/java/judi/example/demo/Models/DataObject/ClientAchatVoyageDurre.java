package judi.example.demo.Models.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Client;
import judi.example.demo.Models.Objects.Durre;
import judi.example.demo.Models.Objects.Genre;
import judi.example.demo.Models.Objects.Voyage;
import judi.example.demo.Models.Objects.VoyageDurre;
import judi.example.demo.Models.Utils.DateHeure;

public class ClientAchatVoyageDurre extends Client{
    int id_vente;
    VoyageDurre voyageDurre;
    DateHeure dateAchat;
   public VoyageDurre getVoyageDurre() {
       return voyageDurre;
   }
   public void setId_vente(int id_vente) {
       this.id_vente = id_vente;
   }
   public int getId_vente() {
       return id_vente;
   }
   public void setVoyageDurre(VoyageDurre voyageDurre) {
       this.voyageDurre = voyageDurre;
   }
   public void setDateAchat(DateHeure dateAchat) {
       this.dateAchat = dateAchat;
   }
   public DateHeure getDateAchat() {
       return dateAchat;
   }
   public ClientAchatVoyageDurre(){}

   
   public void InsertClientAchatVoyageDurre(Connection connection) throws Exception
    {
        String query = "INSERT INTO vente_voyage_durre_client(id_client_fk,id_voyage_fk,id_durre_fk,date_vente) values(?,?,?,'"+this.getDateAchat().getDateTimeString()+"')";

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
            statement.setInt(1, this.getId_client());
            statement.setInt(2, this.getVoyageDurre().getVoyage().getId_voyage());
            statement.setInt(3, this.getVoyageDurre().getDurre().getId());



        
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



    public static ClientAchatVoyageDurre[] getAllClientAchatVoyageDurres(Connection connection) throws Exception{
        String query = "select * from v_vente_voyage_to_client";
        ClientAchatVoyageDurre[] clientAchatVoyageDurres;
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
                clientAchatVoyageDurres = new ClientAchatVoyageDurre[0];
            }else{
                clientAchatVoyageDurres = new ClientAchatVoyageDurre[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    clientAchatVoyageDurres[i] = new ClientAchatVoyageDurre();
                    clientAchatVoyageDurres[i].setId_vente(resultset.getInt("id_vente"));
                    int id_voyage_fk= resultset.getInt("id_voyage_fk");
                    int id_durre_fk = resultset.getInt("id_durre_fk");
                    Voyage v = Voyage.getVoyageById(id_voyage_fk, connection);
                    Durre d = Durre.getDurreById(id_durre_fk, connection);
                    VoyageDurre vd = new VoyageDurre();
                    vd.setVoyage(v);
                    vd.setDurre(d);                
                    clientAchatVoyageDurres[i].setVoyageDurre(vd);
                    clientAchatVoyageDurres[i].setId_client(resultset.getInt("id_client"));
                    clientAchatVoyageDurres[i].setNom(resultset.getString("nom"));
                    clientAchatVoyageDurres[i].setPrenom(resultset.getString("prenom"));
                    clientAchatVoyageDurres[i].setGenre(Genre.getGenreById(resultset.getInt("id_genre_fk"),connection));
                    clientAchatVoyageDurres[i].setDateAchat(new DateHeure(resultset.getString("date_vente")));
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
        return clientAchatVoyageDurres;
    }
    
    public static int getCountClientAchat(Connection connection) throws Exception{
        String query = "select count(*) nombre from v_vente_voyage_to_client";
        ClientAchatVoyageDurre[] clientAchatVoyageDurres;
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
                size = resultset.getInt("nombre");
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
      
    public static int getCountClientHommeAchat(Connection connection) throws Exception{
        String query = "select count(*) nombre from v_vente_voyage_to_client where id_genre=1";
        ClientAchatVoyageDurre[] clientAchatVoyageDurres;
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
                size = resultset.getInt("nombre");
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
           
    public static int getCountClientFemmeAchat(Connection connection) throws Exception{
        String query = "select count(*) nombre from v_vente_voyage_to_client where id_genre=2";
        ClientAchatVoyageDurre[] clientAchatVoyageDurres;
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
                size = resultset.getInt("nombre");
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
