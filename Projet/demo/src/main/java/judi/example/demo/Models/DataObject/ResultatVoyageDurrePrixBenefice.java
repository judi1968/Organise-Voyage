package judi.example.demo.Models.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Durre;
import judi.example.demo.Models.Objects.Voyage;
import judi.example.demo.Models.Objects.VoyageDurre;

public class ResultatVoyageDurrePrixBenefice {
    VoyageDurre voyageDurre;
    double prix_voyageDurre;
    double prix_depense;
    double benefice;
    public void setBenefice(double benefice) {
        this.benefice = benefice;
    }
    public void setPrix_depense(double prix_depense) {
        this.prix_depense = prix_depense;
    }
    public void setPrix_voyageDurre(double prix_voyageDurre) {
        this.prix_voyageDurre = prix_voyageDurre;
    }
    public void setVoyageDurre(VoyageDurre voyageDurre) {
        this.voyageDurre = voyageDurre;
    }
    public VoyageDurre getVoyageDurre() {
        return voyageDurre;
    }
    public double getBenefice() {
        return benefice;
    }
    public double getPrix_depense() {
        return prix_depense;
    }
    public double getPrix_voyageDurre() {
        return prix_voyageDurre;
    }


    public ResultatVoyageDurrePrixBenefice(){

    }

    public static ResultatVoyageDurrePrixBenefice[] getAlResultatVoyageDurrePrixBeneficesInIntervallePrix(double prixMin, double prixMax,Connection connection) throws Exception{
        String query = "select * from v_voyage_durre_prix_voyage_et_prix_depense_and_benefice where benefice> ? and benefice< ?";
        ResultatVoyageDurrePrixBenefice[] voyageDurresBenefice;
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
            statement.setDouble(1, prixMin);
            statement.setDouble(2, prixMax);
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                voyageDurresBenefice = new ResultatVoyageDurrePrixBenefice[0];
            }else{
                voyageDurresBenefice = new ResultatVoyageDurrePrixBenefice[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    voyageDurresBenefice[i] = new ResultatVoyageDurrePrixBenefice();
                    voyageDurresBenefice[i].setVoyageDurre(VoyageDurre.getVoyageByIdVoyageAndIdDurre(resultset.getInt("id_voyage_fk"),resultset.getInt("id_durre_fk"), connection));
                    voyageDurresBenefice[i].setBenefice(resultset.getDouble("benefice"));
                    voyageDurresBenefice[i].setPrix_depense(resultset.getDouble("prix_depense"));
                    voyageDurresBenefice[i].setPrix_voyageDurre(resultset.getDouble("prix_voyage"));
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
        
        return voyageDurresBenefice;
    }
}
