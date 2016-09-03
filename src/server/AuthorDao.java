package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Author;
import data.AuthorList;

public class AuthorDao {
	
	private static AuthorDao instance = null;
	
	private AuthorDao(){}
	
	public static AuthorDao getInstance(){
		if(instance == null){
			instance = new AuthorDao();
		}
		return instance;
	}
	
	public AuthorList getAuthors(){
		ArrayList<Author> authors = new ArrayList<Author>();
		try {
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT authors.id, authors.first_name, authors.last_name "
					+ "FROM authors");
			query.clearParameters();
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	Author a = new Author();
		        a.setId(rs.getInt("authors.id"));
		        a.setFirstName(rs.getString("authors.first_name"));
		        a.setLastName(rs.getString("authors.last_name"));
		        
		        authors.add(a);
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return new AuthorList(authors);
	}
	
	public Author getAuthor(int id){
		Author author = null;
		try {
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT authors.id, authors.first_name, authors.last_name "
					+ "FROM authors "
					+ "WHERE authors.id=?");
			query.clearParameters();
			query.setInt(1, id);
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	author = new Author();
	        	author.setId(rs.getInt("authors.id"));
		        author.setFirstName(rs.getString("authors.first_name"));
		        author.setLastName(rs.getString("authors.last_name"));
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return author;
	}
	
	public void addAuthor(Author author) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"INSERT INTO authors (first_name, last_name) VALUES(?, ?)"
			);
		query.clearParameters();
		query.setString(1, author.getFirstName());
		query.setString(2, author.getLastName());

        query.executeUpdate();
	}
	
	public void deleteAuthor(int id) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"DELETE FROM authors WHERE id=?"
			);
		query.clearParameters();
		query.setInt(1, id);

        query.executeUpdate();
	}
	
	public void updateAuthor(Author author) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"UPDATE authors SET first_name=?, last_name=? WHERE id=?"
			);
		query.clearParameters();
		query.setString(1, author.getFirstName());
		query.setString(2, author.getLastName());
		query.setInt(3, author.getId());

        query.executeUpdate();
	}

}
