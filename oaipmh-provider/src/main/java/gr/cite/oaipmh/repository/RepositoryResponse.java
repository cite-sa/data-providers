package gr.cite.oaipmh.repository;

import javax.ws.rs.core.UriInfo;

import gr.cite.oaipmh.verbs.Verb;
import gr.cite.oaipmh.verbs.VerbError;
import gr.cite.oaipmh.verbs.VerbFactory;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.BadVerbError;

public class RepositoryResponse {
	
	private RepositoryResponse(UriInfo request) {
		
	}
	
	public static RepositoryResponseBuilder request(UriInfo request) {
		return new RepositoryResponseBuilder(request);
	}
	
	
	public static class RepositoryResponseBuilder {
		
		private UriInfo request;
		
		private String response;
		
		private RepositoryResponseBuilder(UriInfo request) {
			this.request = request;
		}
		
		public RepositoryResponseBuilder response() {
			response = request.getAbsolutePath().toString();
			System.out.println(response);
			try {
				Verb verb = VerbFactory.getVerbFactoryMethod(request.getQueryParameters().getFirst("verb"));
				verb.setAttributes(request);
				this.response = verb.response();
			} catch (BadVerbError e) {
				new VerbError();
				this.response = "badVerbError";
			}
			return this;
		}
		
		public String build() {
			return response;
		}
		
		
	}
	
}