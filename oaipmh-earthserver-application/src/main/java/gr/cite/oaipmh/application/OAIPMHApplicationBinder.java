package gr.cite.oaipmh.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import gr.cite.femme.client.FemmeClient;
import gr.cite.femme.client.api.FemmeClientAPI;

public class OAIPMHApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(FemmeClient.class).to(FemmeClientAPI.class);
		
	}
	
}
