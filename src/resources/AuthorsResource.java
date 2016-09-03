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
import data.AuthorList;
import server.AuthorDao;

// http://localhost:8080/server/library/authors
@Path("/library/authors")
public class AuthorsResource {
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public AuthorList getAuthors() {
		AuthorDao authorDao = AuthorDao.getInstance();
		AuthorList authors = authorDao.getAuthors();
		return authors;
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addAuthor(
			@FormParam("first_name") String firstName,
			@FormParam("last_name") String lastName) {

		AuthorDao authorDao = AuthorDao.getInstance();
		Author author = new Author();
		author.setFirstName(firstName);
		author.setLastName(lastName);
		
		try {
			authorDao.addAuthor(author);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Author getAuthor(@PathParam("id") int id) {
		AuthorDao authorDao = AuthorDao.getInstance();
		Author author = authorDao.getAuthor(id);
		return author;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response editAuthor(
			@PathParam("id") int id,
			@FormParam("first_name") String firstName,
			@FormParam("last_name") String lastName) {
		AuthorDao authorDao = AuthorDao.getInstance();
		Author author = new Author();
		author.setId(id);
		author.setFirstName(firstName);
		author.setLastName(lastName);
				
		try {
			authorDao.updateAuthor(author);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteAuthor(@PathParam("id") int id){
		AuthorDao authorDao = AuthorDao.getInstance();
		try {
			authorDao.deleteAuthor(id);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
