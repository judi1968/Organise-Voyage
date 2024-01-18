package judi.example.demo.Models.DatabaseConnection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class Connexion
{
        Connection coOFCo;
    //                                          UTILE

    public Connection makeConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/voyage","postgres","mdpprom15");
            if (connection != null){
                System.out.println("connexion reussie");
                this.coOFCo = connection;
            }
            else{
                System.out.println("connexion échouée");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return connection;
    }

//                                          GET

    public Connection getConnection(){
        return this.coOFCo;
    }

//                                          CONSTRUCTEUR

    public Connexion()
    {
        Connection c = this.makeConnection();
        this.coOFCo = c;
    }
}