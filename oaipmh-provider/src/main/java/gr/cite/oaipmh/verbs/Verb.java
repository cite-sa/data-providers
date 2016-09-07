package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.FlowControl;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.utils.OAIPMH;
import gr.cite.oaipmh.utils.UTCDatetime;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.ErrorCondition;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Verb {
	protected static Logger logger = LoggerFactory.getLogger(Verb.class);
	private List<ErrorCondition> errors = new ArrayList<>(5);

	protected Document xmlDocument;

	public Verb() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.newDocument();
			xmlDocument.setXmlStandalone(true);
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	protected void initializeRootElement() {
		Element rootElement = xmlDocument.createElementNS(OAIPMH.OAI_NAMESPACE,
				"OAI-PMH");
		rootElement.setAttributeNS(XMLUtils.XMLSCHEMA_INSTANCE,
				"xsi:schemaLocation", OAIPMH.OAI_NAMESPACE + " "
						+ OAIPMH.OAI_SCHEMA_LOCATION);
		Element responseDateElement = xmlDocument.createElement("responseDate");
		responseDateElement.setTextContent(UTCDatetime.now());
		rootElement.appendChild(responseDateElement);
		xmlDocument.appendChild(rootElement);
	}

	protected void appendErrorNodes() {
		for (ErrorCondition error : getErrors()) {
			xmlDocument.getDocumentElement().appendChild(XMLUtils.errorToXML(error, xmlDocument));
		}
	}

	protected void addError(ErrorCondition error) {
		errors.add(error);
	}

	public List<ErrorCondition> getErrors() {
		return errors;
	}

	public boolean hasErrors() {
		return (!errors.isEmpty());
	}

	/**
	 * Handles the given {@link ResumptionToken} in the {@link FlowControl}. It
	 * unregisters/registers the {@link ResumptionToken} in the
	 * {@link FlowControl} depending on the current request
	 * 
	 * @param requestElement
	 *            the request element where the attribute of resumptionToken
	 *            will be written to.
	 * @param resumptionTokenString
	 * @param returnedListFromRepositorySize
	 *            the size of the list that the repository returned in the
	 *            current harvesting request
	 */
	protected static void handleResumptionToken(Element requestElement,
			String resumptionTokenString, int returnedListFromRepositorySize) {
		FlowControl flowControl = FlowControl.getInstance();
		ResumptionToken resumptionToken = flowControl
				.getResumptionToken(resumptionTokenString);
		/*
		 * if the current request is the last one, it unregisters the
		 * resumptionToken
		 */
		if (resumptionToken.getCursor() + returnedListFromRepositorySize == resumptionToken
				.getCompleteListSize()) {
			requestElement.setAttribute("resumptionToken", "");
			flowControl.unregister(resumptionToken);
		} else {
			if (!flowControl.contains(resumptionToken)) {
				flowControl.register(resumptionToken);
			}
			requestElement.setAttribute("resumptionToken",
					resumptionToken.toString());
		}
		requestElement.setAttribute("completeListSize",
				String.valueOf(resumptionToken.getCompleteListSize()));
		requestElement.setAttribute("cursor",
				String.valueOf(resumptionToken.getCursor()));

		resumptionToken.setCursor(resumptionToken.getCursor()
				+ returnedListFromRepositorySize);
	}

	abstract public String response(Repository repository);

	abstract public void setAttributes(UriInfo req);
}
