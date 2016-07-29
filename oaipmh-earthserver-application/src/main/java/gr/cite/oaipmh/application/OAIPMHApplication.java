package gr.cite.oaipmh.application;

import org.glassfish.jersey.server.ResourceConfig;

public class OAIPMHApplication extends ResourceConfig {
	public OAIPMHApplication() {
		register(new OAIPMHApplicationBinder());
	}
}
