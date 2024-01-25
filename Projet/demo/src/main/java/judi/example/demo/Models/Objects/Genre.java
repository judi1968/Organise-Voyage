package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;

public class Genre {
    int id_genre;
    String designation_genre;

    public void setdesignation_genre(String designation_genre)throws Exception {
        if(designation_genre.trim().length()==0)
        {
            throw new Exception("designation_genre non valide");
        }
        else 
        {
            this.designation_genre = designation_genre;

        }
    }

    public void setId_genre(int id_genre) {
        this.id_genre = id_genre;
    }

    public String getdesignation_genre() {
        return designation_genre;
    }

    public int getId_genre() {
        return id_genre;
    }

    public static Genre[] getAllGenres(Connection connection) throws Exception{
         String query = "select * from genre_client";
        Genre[] genres;
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
                genres = new Genre[0];
            }else{
                genres = new Genre[size];
                int i = 0;
                resultset =  statement.executeQuery();
                while(resultset.next()){
                    genres[i] = new Genre();
                    genres[i].setId_genre(resultset.getInt("id_genre"));
                    genres[i].setdesignation_genre(resultset.getString("designation_genre"));
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
        return genres;
    }

    //get by id
    public static Genre getGenreById(int id,Connection connection) throws Exception {
		String query = "select * from genre_client where id_genre=?";
        PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
        Genre genre = new Genre();
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
                genre.setId_genre(resultset.getInt("id_genre"));
                genre.setdesignation_genre(resultset.getString("designation_genre"));

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
        return genre;
    }



}

