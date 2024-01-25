package judi.example.demo.Models.Objects;
import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;
import judi.example.demo.Models.Utils.DateHeure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Employe {
    int id_employe;
    String fonction_designantion;
    Double prix;
    DateHeure dateEmbauche;
    FonctionEmploye fonction;
    public void setFonction(FonctionEmploye fonction) {
        this.fonction = fonction;
    }
    public FonctionEmploye getFonction() {
        return fonction;
    }
    public void setDateEmbauche(DateHeure dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }
    public DateHeure getDateEmbauche() {
        return dateEmbauche;
    }

    public int getId_employe() {
        return id_employe;
    }

    public String getFonction_designantion() {
        return fonction_designantion;
    }

    public Double getPrix() {
        return prix;
    }

    public void setId_employe(int id_employe) {
        this.id_employe = id_employe;
    }

    public void setFonction_designantion(String fonction_designantion) {
        this.fonction_designantion = fonction_designantion;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Employe(){

    }

    public Employe(int id, String fonction, double prix,FonctionEmploye fonctionEmploye){
        setId_employe(id);
        setFonction_designantion(fonction);
        setPrix(prix);
        setFonction(fonctionEmploye);
    }

    //insert
    public void insertNewEmploye(Connection connection) throws Exception{
        String query = "INSERT INTO employe ( fonction_designation, prix,date_embauche, id_fonction_fk) VALUES ( ?, ? , ?, ?);";
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
            statement.setString(1, this.getFonction_designantion());
            statement.setDouble(2, this.getPrix());
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(dateEmbauche.getDate(), dateEmbauche.getHeure())));
            statement.setInt(4, this.getFonction().getId_fonction());

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

    //get all
    public static Employe[] getAllEmploye(Connection connection) throws Exception{
         String query = "select * from employe";
        Employe[] employes;
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
                employes = new Employe[0];
            }else{
                employes = new Employe[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    employes[i] = new Employe();
                    employes[i].setId_employe(resultset.getInt("id_employe"));
                    employes[i].setFonction_designantion(resultset.getString("fonction_designation"));
                    employes[i].setPrix(resultset.getDouble("prix"));
                    employes[i].setFonction(FonctionEmploye.getFonctionEmployeById(resultset.getInt("id_fonction_fk"), connection));
                    employes[i].setDateEmbauche(new DateHeure(resultset.getString("date_embauche")));
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
        return employes;
    }

    //get by id
    public static Employe getEmployeById(int id,Connection connection) throws Exception {
		String query = "select * from employe where id_employe=?";
        int size = 0;
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        Employe emp = new Employe();
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
                emp.setId_employe(resultset.getInt("id_Employe"));
                emp.setFonction_designantion(resultset.getString("fonction_designation"));
                emp.setPrix(resultset.getDouble("prix"));
                emp.setFonction(FonctionEmploye.getFonctionEmployeById(resultset.getInt("id_fonction_fk"), connection));
                emp.setDateEmbauche(new DateHeure(resultset.getString("date_embauche")));
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
        return emp;
    }

    public static Employe[] getAllEmployerWithTauxHorraireAndNiveau(DateHeure dateDonne,Connection connection) throws Exception{
        String query = "select *,"+dateDonne.getDateTimeString()+"-date_embauche nombre_jours from employe where date_embauche< ?";
       Employe[] employes;
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
           statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(dateDonne.getDate(), dateDonne.getHeure())));
           resultset =  statement.executeQuery();
           
           while(resultset.next()) {
               size++;
           }
           if(size==0){
               employes = new Employe[0];
           }else{
               employes = new Employe[size];
               int i = 0;
               resultset =  statement.executeQuery();
               while(resultset.next()){
                   employes[i] = new Employe();
                   employes[i].setId_employe(resultset.getInt("id_employe"));
                   employes[i].setFonction_designantion(resultset.getString("fonction_designation"));
                   employes[i].setPrix(resultset.getDouble("prix"));
                    employes[i].setFonction(FonctionEmploye.getFonctionEmployeById(resultset.getInt("id_fonction"), connection));
                    employes[i].setDateEmbauche(new DateHeure(resultset.getString("date_embauche")));
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
       return employes;
   }
}
