package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Durre;
import judi.example.demo.Models.Objects.Employe;
import judi.example.demo.Models.Objects.Voyage;
public class VoyageDurre {
    Voyage voyage;
    Durre durre;
    public void setDurre(Durre durre) {
        this.durre = durre;
    }
    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }
    public Durre getDurre() {
        return durre;
    }
    
    public Voyage getVoyage() {
        return voyage;
    }



    public static VoyageDurre[] getAllVoyageDurre(Connection connection) throws Exception{
        String query = "select * from v_voyage_durre";
        VoyageDurre[] voyageDurres;
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
                voyageDurres = new VoyageDurre[0];
            }else{
                voyageDurres = new VoyageDurre[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyageDurres[i] = new VoyageDurre();
                    voyageDurres[i].setDurre(Durre.getDurreById(resultset.getInt("id_durre_fk"), connection));
                    voyageDurres[i].setVoyage(Voyage.getVoyageById(resultset.getInt("id_voyage_fk"), connection));
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
        
        return voyageDurres;
    }
      
    public static VoyageDurre getVoyageByIdVoyageAndIdDurre(int id_voyage,int id_durre,Connection connection) throws Exception{
        String query = "select * from v_voyage_durre where id_voyage_fk= ? and id_durre_fk= ? ";
        VoyageDurre voyageDurre;
        voyageDurre = new VoyageDurre();
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
            statement.setInt(1, id_voyage);
            statement.setInt(2, id_durre);

			statementOpen = true;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyageDurre.setDurre(Durre.getDurreById(resultset.getInt("id_durre_fk"), connection));
                    voyageDurre.setVoyage(Voyage.getVoyageById(resultset.getInt("id_voyage_fk"), connection));
                   
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
        return voyageDurre;
    }

    public void insertEmployerToVoyageDurre(Employe emp,double temps ,Connection connection) throws Exception
    {
        // System.out.println("igi");
        String query = "INSERT INTO voyage_durre_employer(id_voyage_fk,id_durre_fk,id_employe_fk,durre_number) values(?,?,?,?)";

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
            statement.setInt(1, this.getVoyage().getId_voyage());
            statement.setInt(2, this.getDurre().getId());
            statement.setInt(3, emp.getId_employe());
            statement.setDouble(4, temps);

        
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
