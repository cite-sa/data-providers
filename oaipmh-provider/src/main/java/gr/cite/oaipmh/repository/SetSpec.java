package gr.cite.oaipmh.repository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaeus.utils.xml.XMLFormatter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gr.cite.oaipmh.metadata.DcItem;

public class SetSpec {
	private String setSpec;
	private String setName;
	private DcItem setDescription;

	public SetSpec(String setSpec) {
		this.setSpec = setSpec;
	}

	public SetSpec(String setSpec, String setName) {
		this.setSpec = setSpec;
		this.setName = setName;
	}

	public SetSpec(String setSpec, String setName, DcItem setDescription) {
		this.setSpec = setSpec;
		this.setName = setName;
		this.setDescription = setDescription;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public void setSetDescription(DcItem setDescription) {
		this.setDescription = setDescription;
	}

	@Override
	public String toString() {
		return this.setName;
	}

	public Element getXMLElement() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDocument = builder.newDocument();

		Element setElement = xmlDocument.createElement("set");
		Element specElement = xmlDocument.createElement("setSpec");
		specElement.setTextContent(setSpec);
		setElement.appendChild(specElement);

		if (this.setName!= null) {
			Element nameElement = xmlDocument.createElement("setName");
			nameElement.setTextContent(setName);
			setElement.appendChild(nameElement);
		}

		if (this.setDescription != null) {
			Element descriptionElement = xmlDocument.createElement("setDescription");
			descriptionElement.appendChild(xmlDocument.importNode(this.setDescription.getXMLElement(), true));
			setElement.appendChild(descriptionElement);
		}

		return setElement;
	}
}
