package gr.cite.oaipmh.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import gr.cite.oaipmh.application.resources.OaiPmhResource;

@ApplicationPath("restAPI")
public class OaiPmhApplication extends ResourceConfig {
	
	public OaiPmhApplication() {
		register(OaiPmhResource.class);
	}
	
}
