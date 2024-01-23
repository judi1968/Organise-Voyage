package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.DatabaseConnection.Connexion;

public class ActiviteVoyage {
    Voyage voyage;
    Activite activite;
    Durre durre;
    int quantite;

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
    public void setDurre(Durre durre) {
        this.durre = durre;
    }
    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Activite getActivite() {
        return activite;
    }
    public Durre getDurre() {
        return durre;
    }
    public Voyage getVoyage() {
        return voyage;
    }

    public ActiviteVoyage(){}
    public ActiviteVoyage(Voyage voyage,Activite activite,Durre durre){
        setActivite(activite);
        setDurre(durre);
        setVoyage(voyage);
    }

    public void insertNewActiviteVoyage(Connection connection) throws Exception{
        String query = "INSERT INTO activite_voyage ( id_voyage_fk, id_activite_fk, id_durre_fk) VALUES ( ?, ?, ? );";
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
            statement.setInt(1, this.getVoyage().getId_voyage());
            statement.setInt(2, this.getActivite().getId_activite());
            statement.setInt(3, this.getDurre().getId());

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

    // 
    
    public static ActiviteVoyage[] getAllActiviteVoyage(Connection connection) throws Exception{
        String query = "select * from v_voyage_durre order by id_voyage_fk asc,id_durre_fk asc";
        ActiviteVoyage[] activites;
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
                activites = new ActiviteVoyage[0];
            }else{
                activites = new ActiviteVoyage[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    activites[i] = new ActiviteVoyage();
                    activites[i].setVoyage(Voyage.getVoyageById(resultset.getInt("id_voyage_fk"), connection));
                    activites[i].setDurre(Durre.getDurreById(resultset.getInt("id_durre_fk"), connection));
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

    
    public static ActiviteVoyage getActiviteVoyageByIdVoyageAndIdDurre(int id_v,int id_c,Connection connection) throws Exception{
        String query = "select * from v_voyage_durre where id_voyage_fk=? and id_durre_fk=?";
        ActiviteVoyage activites = new ActiviteVoyage();
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
            statement.setInt(1, id_v);
            statement.setInt(2, id_c);
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
            while(resultset.next()){
                    activites.setVoyage(Voyage.getVoyageById(resultset.getInt("id_voyage_fk"), connection));
                    activites.setDurre(Durre.getDurreById(resultset.getInt("id_durre_fk"), connection));
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
    
}
