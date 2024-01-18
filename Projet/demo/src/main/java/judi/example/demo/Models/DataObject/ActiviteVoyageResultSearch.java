package judi.example.demo.Models.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Objects.Activite;
import judi.example.demo.Models.Objects.ActiviteVoyage;
import judi.example.demo.Models.Objects.Durre;
import judi.example.demo.Models.Objects.Voyage;

public class ActiviteVoyageResultSearch extends ActiviteVoyage {
    int nombre_activite;
    public void setNombre_activite(int nombre_activite) {
        this.nombre_activite = nombre_activite;
    }
    public int getNombre_activite() {
        return nombre_activite;
    }

    public ActiviteVoyageResultSearch(){}
    public ActiviteVoyageResultSearch(Voyage voyage,Activite activite,Durre durre,int nombre){
        super(voyage, activite, durre);
        setNombre_activite(nombre);
    }

    public static ActiviteVoyageResultSearch[] getAllActiviteVoyageResultByIdActivite(Activite activite,Connection connection) throws Exception{
        String query = "select id_voyage_fk,id_durre_fk,count(*) nombre from activite_voyage where id_activite_fk= ? group by id_voyage_fk ,id_durre_fk order by id_voyage_fk asc,id_durre_fk asc";
        ActiviteVoyageResultSearch[] results;
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        //select id_voyage_fk,id_durre_fk,count(*) nombre from activite_voyage group by id_voyage_fk ,id_durre_fk order by id_voyage_fk asc,id_durre_fk asc
		try {
            if(connection==null) {
                connection = ConnectionPostgres.connect("localhost",5432,"voyage","postgres","mdpprom15");
				connection.setAutoCommit(false);
                closeable = true;
			}
			
			statement = connection.prepareStatement(query);
            statement.setInt(1,activite.getId_activite());
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
                size++;
			}
            if(size==0){
                results = new ActiviteVoyageResultSearch[0];
            }else{
                results = new ActiviteVoyageResultSearch[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    results[i] = new ActiviteVoyageResultSearch();
                    results[i].setActivite(activite);
                    results[i].setDurre(Durre.getDurreById(resultset.getInt("id_durre_fk"), connection));
                    results[i].setVoyage(Voyage.getVoyageById(resultset.getInt("id_voyage_fk"), connection));
                    results[i].setNombre_activite(resultset.getInt("nombre"));
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
        return results;
    }
}
