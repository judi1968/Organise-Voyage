
package judi.example.demo.Models.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Voyage;
public class Voyage_prix extends Voyage
{
    double prix;

    public double getPrix() {
        return prix;
    }

    public void setPrix(double p){
        this.prix= p;
    }

    public Voyage_prix()
    {

    }

    public Voyage_prix(double prix)
    {
        setPrix(prix);
    }


    public static Voyage_prix[] getAllVoyagesPrix(double min, double max, Connection connection) throws Exception{
                    System.out.println(min+" "+max);

        String query = "select * from v_voyage_prix_actuelle where prix<=? and prix>=?";
        Voyage_prix[] voyages_prix;
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
            statement.setDouble(1, max);
            statement.setDouble(2, min);

			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                voyages_prix = new Voyage_prix[0];
            }else{
                voyages_prix = new Voyage_prix[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyages_prix[i] = new Voyage_prix();
                    voyages_prix[i].setNom_voyage(Voyage.getVoyageById(resultset.getInt("id_voyage"), connection).getNom_voyage());
                    voyages_prix[i].setPrix(resultset.getDouble("prix"));
                    System.out.println(voyages_prix[i].getNom_voyage());
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
        return voyages_prix;
    }
}
