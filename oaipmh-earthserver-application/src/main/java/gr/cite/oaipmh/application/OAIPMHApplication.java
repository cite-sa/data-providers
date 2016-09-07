package gr.cite.oaipmh.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import gr.cite.oaipmh.resources.OAIPMHResource;

@ApplicationPath("restAPI")
public class OAIPMHApplication extends ResourceConfig {
	
	public OAIPMHApplication() {
		register(OAIPMHResource.class);
	}
	
}
