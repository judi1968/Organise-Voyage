package judi.example.demo.Models.DatabaseConnection;

import java.sql.*;

public class Query
{
    Connection coOfQuery;
    ResultSet queryResults;
    Statement statOfQuery;

//                                              GET
    
    public Connection getConnection(){
        return this.coOfQuery;
    }
    public ResultSet getResultSet(){
        return this.queryResults;
    }
    public Statement getStatement(){
        return this.statOfQuery;
    }
//                                              SET
    public void setResultSet(ResultSet r){
        this.queryResults = r;
    }
//                                              CONSTRUCTEUR
    public void execute(String queryType, String requete){
        ResultSet resultset = null;
        try{
            Connexion c = new Connexion();
            this.coOfQuery = c.getConnection();
            Statement statement = c.getConnection().createStatement();
            this.statOfQuery = statement;
            if (queryType.compareTo("select")==0){
                resultset = statement.executeQuery(requete);
                this.queryResults =  resultset;
            }
            else{
                statement.execute(requete);
            }
        } catch (Exception e){
            //JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            e.printStackTrace();
        }
    }
//                                              UTILE
    public String makeNewString(Object o){
        String s = String.valueOf(o);
        StringBuffer sbf = new StringBuffer(s);
        sbf.insert(0, '\'');
        sbf.insert(sbf.length(), '\'');
        s = sbf.toString();
        return s;
    }
    public Query(){}
}