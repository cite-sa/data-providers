package gr.cite.oaipmh.metadata;

import gr.cite.oaipmh.utils.OAIPMH;

import javax.xml.bind.annotation.XmlRootElement;

public class OaiDcItem extends DcItem {

	public OaiDcItem() {
		super("oai_dc", OAIPMH.OAI_SCHEMA_LOCATION, OAIPMH.OAI_NAMESPACE);
	}

}
