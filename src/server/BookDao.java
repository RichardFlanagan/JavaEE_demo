package server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Author;
import data.Book;
import data.BookList;
import data.Publisher;

public class BookDao {
	
	private static BookDao instance = null;
	
	private BookDao(){}
	
	public static BookDao getInstance(){
		if(instance == null){
			instance = new BookDao();
		}
		return instance;
	}
	
	public BookList getBooks(){
		ArrayList<Book> books = new ArrayList<Book>();
		try {			
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT books.id, books.title, books.year, books.author, books.publisher, authors.first_name, authors.last_name, publishers.name "
					+ "FROM books, authors, publishers "
					+ "WHERE books.author=authors.id AND books.publisher=publishers.id");
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	Book book = new Book();
	        	book.setId(rs.getInt("books.id"));
	        	book.setTitle(rs.getString("books.title"));
	        	book.setPublishYear(rs.getString("books.year"));

	        	Author author = new Author();
	        	author.setId(rs.getInt("books.author"));
	        	author.setFirstName(rs.getString("authors.first_name"));
	        	author.setLastName(rs.getString("authors.last_name"));
	        	book.setAuthor(author);
	        	
	        	Publisher publisher = new Publisher();
	        	publisher.setId(rs.getInt("books.publisher"));
	        	publisher.setName(rs.getString("publishers.name"));
	        	book.setPublisher(publisher);
	        	
	        	books.add(book);
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return new BookList(books);
	}
	
	public Book getBook(int id){
		Book book = null;
		try {
			PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
					"SELECT books.id, books.title, books.year, books.author, books.publisher, authors.first_name, authors.last_name, publishers.name "
					+ "FROM books, authors, publishers "
					+ "WHERE books.author=authors.id AND books.publisher=publishers.id AND books.id=?");
			query.clearParameters();
			query.setInt(1, id);
	
	        ResultSet rs = query.executeQuery();
	        
	        while(rs.next()){
	        	book = new Book();
	        	book.setId(rs.getInt("books.id"));
	        	book.setTitle(rs.getString("books.title"));
	        	book.setPublishYear(rs.getString("books.year"));
	        	
	        	Author a = new Author();
	        	a.setId(rs.getInt("books.author"));
	        	a.setFirstName(rs.getString("authors.first_name"));
	        	a.setLastName(rs.getString("authors.last_name"));
	        	book.setAuthor(a);
	        	
	        	Publisher p = new Publisher();
	        	p.setId(rs.getInt("books.publisher"));
	        	p.setName(rs.getString("publishers.name"));
	        	book.setPublisher(p);
	        }   
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		return book;
	}
	
	public void addBook(Book book) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"INSERT INTO books (title, year, author, publisher) "
				+ " VALUES(?, ?, ?, ?)"
			);
		query.clearParameters();
		query.setString(1, book.getTitle());
		query.setString(2, book.getPublishYear());
		query.setInt(3, book.getAuthor().getId());
		query.setInt(4, book.getPublisher().getId());

        query.executeUpdate();
	}
	
	public void deleteBook(int id) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"DELETE FROM books WHERE id=?"
			);
		query.clearParameters();
		query.setInt(1, id);

        query.executeUpdate();
	}
	
	public void updateBook(Book book) throws SQLException{
		PreparedStatement query = DatabaseManager.getConnection().prepareStatement(
				"UPDATE books SET title=?, year=?, author=?, publisher=? WHERE id=?"
			);
		query.clearParameters();
		query.setString(1, book.getTitle());
		query.setString(2, book.getPublishYear());
		query.setInt(3, book.getAuthor().getId());
		query.setInt(4, book.getPublisher().getId());
		query.setInt(5, book.getId());
		
        query.executeUpdate();
	}
	
}
