package gr.cite.oaipmh.repository;

import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.utils.UTCDatetime;
import gr.cite.oaipmh.utils.XMLUtils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Ioannis Kavvouras
 *
 */
public class Record {
	protected static Logger logger = LoggerFactory.getLogger(Record.class);

	private final String id;
	private final String metadataPrefix;
	private Metadata metadata;
	private UTCDatetime datetime;
	private boolean isDeleted = false;
	private List<SetSpec> setSpecs = new ArrayList<SetSpec>();

	private Document xmlDocument;

	public Element aboutElement = null;

	public Record(String id, String metadataPrefix) {
		this.id = id;
		this.metadataPrefix = metadataPrefix;
		initXML();
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public String getId() {
		return id;
	}

	public List<SetSpec> getSetSpecs() {
		return setSpecs;
	}

	public void setSetSpecs(List<SetSpec> setSpecs) {
		this.setSpecs = setSpecs;
	}

	public UTCDatetime getDatetime() {
		if (datetime == null) {
			return datetime = new UTCDatetime(UTCDatetime.now());
		}
		return datetime;
	}

	public void setDatetime(UTCDatetime datetime) {
		this.datetime = datetime;
	}

	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	private void initXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public Element getXMLElement() {

		Element recordElement = xmlDocument.createElement("record");

		recordElement.appendChild(getHeaderAsXMLElement());
		Element metadataElement = xmlDocument.createElement("metadata");
		try {

			// System.out.println(XMLUtils.transformDocumentToString(metadata
			// .getXMLElement()));

			metadataElement.appendChild(xmlDocument.importNode(
					metadata.getXMLElement(), true));
		} catch (DOMException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		recordElement.appendChild(metadataElement);
		if (aboutElement != null) {
			recordElement.appendChild(aboutElement);
		}
		return recordElement;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Element getHeaderAsXMLElement() {
		Element headerElement = xmlDocument.createElement("header");
		if (isDeleted) {
			headerElement.setAttribute("status", "deleted");
		}
		Element idElement = xmlDocument.createElement("identifier");
		idElement.setTextContent(id);
		headerElement.appendChild(idElement);
		Element datestampElement = xmlDocument.createElement("datestamp");
		datestampElement.setTextContent(getDatetime().getDatetimeAsString());
		headerElement.appendChild(datestampElement);

		for (SetSpec setSpec : setSpecs) {
			Element setElement = xmlDocument.createElement("setSpec");
			setElement.setTextContent(setSpec.toString());
			headerElement.appendChild(setElement);
		}

		return headerElement;
	}
}
