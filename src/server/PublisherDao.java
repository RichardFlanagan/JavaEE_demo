package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Publisher;
import data.PublisherList;

public class PublisherDao {
	
	private static PublisherDao instance = null;
	
	private PublisherDao(){}
	
	public static PublisherDao getInstance(){
		if(instance == null){
			instance = new PublisherDao();
		}
		return instance;
	}
	
	public PublisherList getPublishers(){
	//public ArrayList<Publisher> getPublishers(){
		ArrayList<Publisher> publishers = new ArrayList<Publisher>();
		try {
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT publishers.id, publishers.name "
					+ "FROM publishers");
			query.clearParameters();
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	Publisher p = new Publisher();
			    p.setId(rs.getInt("publishers.id"));
			    p.setName(rs.getString("publishers.name"));
			        
	        	publishers.add(p);
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return new PublisherList(publishers);
		//return publishers;
	}
	
	public Publisher getPublisher(int id){
		Publisher publisher = null;
		try {
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT publishers.id, publishers.name "
					+ "FROM publishers "
					+ "WHERE publishers.id=?");
			query.clearParameters();
			query.setInt(1, id);
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	publisher = new Publisher();
	        	publisher.setId(rs.getInt("publishers.id"));
	        	publisher.setName(rs.getString("publishers.name"));
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return publisher;
	}
	
	public void addPublisher(Publisher publisher) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"INSERT INTO publishers (name) VALUES (?)"
			);
		query.clearParameters();
		query.setString(1, publisher.getName());

        query.executeUpdate();
	}
	
	public void deletePublisher(int id) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"DELETE FROM publishers WHERE id=?"
			);
		query.clearParameters();
		query.setInt(1, id);

        query.executeUpdate();
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"UPDATE publishers SET name=?  WHERE id=?"
			);
		query.clearParameters();
		query.setString(1, publisher.getName());
		query.setInt(2, publisher.getId());

        query.executeUpdate();
	}

}
