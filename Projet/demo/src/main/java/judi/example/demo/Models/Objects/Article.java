package judi.example.demo.Models.Objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import judi.example.demo.Models.DatabaseConnection.ConnectionPostgres;


public class Article {

    private String idArticle;
    private String designationArticle;
    private int idTypeArticle;

    // Constructeur par défaut
    public Article() {
    }

    // Constructeur avec tous les champs
    public Article(String idArticle, String designationArticle, int idTypeArticle) {
        this.idArticle = idArticle;
        this.designationArticle = designationArticle;
        this.idTypeArticle = idTypeArticle;
    }

    // Getters et Setters
    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }

    public String getDesignationArticle() {
        return designationArticle;
    }

    public void setDesignationArticle(String designationArticle) {
        this.designationArticle = designationArticle;
    }

    public int getIdTypeArticle() {
        return idTypeArticle;
    }

    public void setIdTypeArticle(int idTypeArticle) {
        this.idTypeArticle = idTypeArticle;
    }

    public static Vector<Article> getAllArticles(Connection connection) throws Exception {
		String query = "select * from articles";
		Vector<Article> articles = new Vector<>();
		PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
		try {
			if(connection==null) {
				closeable = true;
				connection = ConnectionPostgres.connect("localhost",5432,"stock2","postgres","mdpprom15");
				connection.setAutoCommit(false);
			}
			
			statement = connection.prepareStatement(query);
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
				Article article = new Article(resultset.getString("id_article"),resultset.getString("designation_article"),resultset.getInt("id_type_article_fk"));
				articles.add(article);
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
		return articles;
	}
 
	public Vector<Article> getAllArticlesLikeId(Connection connection) throws Exception {
		String id_article = getIdArticle();
		boolean isStock = false;
		String last_id_article = id_article.substring(id_article.length()-1);
		if(last_id_article.compareToIgnoreCase("S") == 0) {
			isStock = true;
			id_article = id_article.substring(0,id_article.length()-1);
		}
		String query = "";
		if(isStock) {
			query =  "select * from articles where id_article like ? and id_article like '%S'"; 
		}
		else { query = "select * from articles where id_article like ? and id_article not like '%S'"; }
		
		Vector<Article> articles = new Vector<>();
		PreparedStatement statement = null;
		ResultSet resultset= null;
		boolean statementOpen = false;
		boolean resultsetOpen = false;
		boolean closeable = false;
		try {
			if(connection==null) {
				closeable = true;
				connection = ConnectionPostgres.connect("localhost",5432,"stock2","postgres","mdpprom15");
				connection.setAutoCommit(false);
			}
			
			statement = connection.prepareStatement(query);
			statement.setString(1, id_article+"%");
			statementOpen = true;
			
			resultset =  statement.executeQuery();
			
			while(resultset.next()) {
				Article articleObj = Article.getArticlesById(resultset.getString("id_article"), connection);
				articles.add(articleObj);
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
		return articles;
	}

	public static Article getArticlesById(String id,Connection connection) throws Exception {
		Article a = null;
		boolean closeable = false;
		try {
			
			if(connection==null) {
				closeable = true;
				connection = ConnectionPostgres.connect("localhost",5432,"stock2","postgres","mdpprom15");
				connection.setAutoCommit(false);
			}
			Vector<Article> articles = Article.getAllArticles(connection);
			for (Article article : articles) {
				if(article.getIdArticle().compareToIgnoreCase(id)==0) {
					a = article;
				}
			}
			if(a==null) {
				throw new Exception("On a pas trouver un article dont l'Id est : "+id);
			}else {
				return a;
			}
		}catch (Exception e) {
			throw e;
		}finally {
			if(closeable) {
				connection.commit();
				connection.close();
			}
		}
	}
}
