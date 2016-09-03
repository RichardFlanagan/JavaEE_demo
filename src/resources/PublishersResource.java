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

import data.Publisher;
import data.PublisherList;
import server.PublisherDao;


// http://localhost:8080/server/library/publishers
@Path("/library/publishers")
public class PublishersResource {
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public PublisherList getPublishers() {
		PublisherDao publisherDao = PublisherDao.getInstance();
		PublisherList publishers = publisherDao.getPublishers();
		return publishers;
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addPublisher(
			@FormParam("name") String name) {

		PublisherDao publisherDao = PublisherDao.getInstance();
		Publisher p = new Publisher();
		p.setName(name);
		
		try {
			publisherDao.addPublisher(p);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.TEXT_XML)
	public Publisher getPublisher(@PathParam("id") int id) {
		PublisherDao publisherDao = PublisherDao.getInstance();
		Publisher publisher = publisherDao.getPublisher(id);
		return publisher;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response editPublisher(
			@PathParam("id") int id,
			@FormParam("name") String name) {
		PublisherDao publisherDao = PublisherDao.getInstance();
		Publisher publisher = new Publisher();
		publisher.setId(id);
		publisher.setName(name);
				
		try {
			publisherDao.updatePublisher(publisher);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deletePublisher(@PathParam("id") int id){
		PublisherDao publisherDao = PublisherDao.getInstance();
		try {
			publisherDao.deletePublisher(id);
		} catch (SQLException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok().build();
	}

}
