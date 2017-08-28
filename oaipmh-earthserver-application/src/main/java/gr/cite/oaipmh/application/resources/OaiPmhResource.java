package gr.cite.oaipmh.application.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryResponse;

@Component
@Path("")
@Produces(MediaType.TEXT_XML)
public class OaiPmhResource {
	private static final Logger logger = LoggerFactory.getLogger(OaiPmhResource.class);
	
	private Repository repository;
	
	@Inject
	public OaiPmhResource(Repository repository) {
		this.repository = repository;
	}
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.ok("pong").build();
	}
	
	@GET
	@Path("/provider")
	public Response query(@Context UriInfo request) {
		String response = RepositoryResponse.request(request).response(repository).build();
		/*try {
			RepositoryConnectionFactory.getRepository().closeConnection();
		} catch (RepositoryRegistrationException e) {
			logger.error(e.getMessage(), e);
		}*/
		return Response.ok(response).build();
	}
}
