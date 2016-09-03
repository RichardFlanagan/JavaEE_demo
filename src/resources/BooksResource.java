package resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.Author;
import data.Book;
import data.BookList;
import data.Publisher;
import server.BookDao;

// http://localhost:8080/server/library/books
@Path("/library/books")
public class BooksResource {
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public BookList getBooks() {
		BookDao bookDao = BookDao.getInstance();
		BookList books = bookDao.getBooks();
		return books;
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addBook(
			@FormParam("title") String title,
			@FormParam("year") String year,
			@FormParam("author") int authorId,
			@FormParam("publisher") int publisherId) {

		BookDao bookDao = BookDao.getInstance();
		try {
			Book book = new Book();
			book.setTitle(title);
			book.setPublishYear(year);
			
			Author a = new Author();
			a.setId(authorId);
			book.setAuthor(a);
			
			Publisher p = new Publisher();
			p.setId(publisherId);
			book.setPublisher(p);
			
			bookDao.addBook(book);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Book getBook(@PathParam("id") int id) {
		BookDao bookDao = BookDao.getInstance();
		Book book = bookDao.getBook(id);
		return book;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response editBook(
			@PathParam("id") int id,
			@FormParam("title") String title,
			@FormParam("year") String year,
			@FormParam("author") int authorId,
			@FormParam("publisher") int publisherId) {
		BookDao bookDao = BookDao.getInstance();
		
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setPublishYear(year);
		
		Author a = new Author();
		a.setId(authorId);
		book.setAuthor(a);
		
		Publisher p = new Publisher();
		p.setId(publisherId);
		book.setPublisher(p);
		
		try {
			bookDao.updateBook(book);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteBook(@PathParam("id") int id){
		BookDao bookDao = BookDao.getInstance();
		try {
			bookDao.deleteBook(id);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
