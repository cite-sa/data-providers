package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.CannotDisseminateFormatError;
import gr.cite.oaipmh.verbs.errors.IdDoesNotExistError;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class GetRecord extends Verb {
	
	protected static Logger logger = LoggerFactory.getLogger(GetRecord.class);

	private String requestURL;
	private String identifier;
	private String metadataPrefix;

	public String response() {
		initializeRootElement();
		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setAttribute("verb", "GetRecord");
		requestElement.setAttribute("identifier", identifier);
		requestElement.setAttribute("metadataPrefix", metadataPrefix);
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);
		Element getRecordElement = xmlDocument.createElement("GetRecord");
		if (this.hasErrors()) {
			appendErrorNodes();
		} else {
			Repository repository;
			try {
				repository = RepositoryConnectionFactory.getRepository();
			} catch (RepositoryRegistrationException e1) {
				logger.error(e1.getMessage(), e1);
				return null;
			}
			try {
				Record record = repository
						.getRecord(identifier, metadataPrefix);
				getRecordElement.appendChild(xmlDocument.importNode(
						record.getXMLElement(), true));
				rootElement.appendChild(getRecordElement);
			} catch (IdDoesNotExistError e) {
				rootElement.appendChild(XMLUtils.errorToXML(e, xmlDocument));
				logger.info("ErrorCondition: " + e.getCode());
			} catch (CannotDisseminateFormatError e) {
				rootElement.appendChild(XMLUtils.errorToXML(e, xmlDocument));
				logger.info("ErrorCondition: " + e.getCode());
			}
		}

		try {
			return XMLUtils.transformDocumentToString(xmlDocument);
		} catch (XMLConversionException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void setAttributes(UriInfo req) {
		/*requestURL = req.getRequestURL().toString();
		Set<String> attributes = new HashSet<String>(Collections.list(req
				.getParameterNames()));

		attributes.remove("verb");

		if (attributes.size() > 2) {
			addError(new BadArgumentError());
		} else if (!(attributes.contains("identifier") && attributes
				.contains("metadataPrefix"))) {
			addError(new BadArgumentError());
		} else {
			identifier = req.getParameter("identifier");
			metadataPrefix = req.getParameter("metadataPrefix");
		}*/
	}

}
