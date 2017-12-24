package pl.exam.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("")
public class Test
{
	@GET
	@Path("qwe")
	@Produces("application/json")
	public Response ping()
	{
		return Response.ok(true).build();
	}
}