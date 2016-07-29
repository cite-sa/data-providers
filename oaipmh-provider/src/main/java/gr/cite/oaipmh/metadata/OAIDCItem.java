package gr.cite.oaipmh.metadata;

import gr.cite.oaipmh.utils.OAIPMH;

/**
 * OAI {@link DCItem dublin core item}
 * @author Ioannis Kavvouras
 *
 */
public class OAIDCItem extends DCItem {

	public OAIDCItem() {
		super("oai_dc", OAIPMH.OAI_SCHEMA_LOCATION, OAIPMH.OAI_NAMESPACE);
	}

}
