package gr.cite.oaipmh.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.repository.RepositoryResponse;
import gr.cite.oaipmh.verbs.Verb;
import gr.cite.oaipmh.verbs.VerbFactory;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;

@Path("/oaipmh")
@Produces(MediaType.TEXT_XML)
public class OAIPMHResource {
	
	Logger logger = LoggerFactory.getLogger(OAIPMHResource.class);
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "pong";
	}
	
	@GET
	@Path("/provider")
	public Response query(@Context UriInfo request) {
		String response = RepositoryResponse.request(request).response().build();
		try {
			RepositoryConnectionFactory.getRepository().closeConnection();
		} catch (RepositoryRegistrationException e) {
			logger.error(e.getMessage(), e);
		}
		return Response.ok(response).build();
	}
}
