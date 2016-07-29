package gr.cite.oaipmh.application.femme;

import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.utils.OAIPMH;

import org.w3c.dom.Element;

public class XSLTMetadataFromRepository extends Metadata {

	private final Element xmlElement;

	public XSLTMetadataFromRepository(Element xmlElement) {
		super("oai_dc", OAIPMH.OAI_SCHEMA_LOCATION, OAIPMH.OAI_NAMESPACE);

		this.xmlElement = xmlElement;
	}

	@Override
	public Element getXMLElement() throws Exception {
		return xmlElement;
	}
	

}
