package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// http://localhost:8080/server/library
@Path("/library")
public class IndexResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String index() {
		return "<div><h1>Book Info System</h1><p>Please use the butons to the left select an option</p><p>Made by Richard Flanagan :: A00193644</p></div>";
	}

}
