package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.utils.OAIPMH;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class Identify extends Verb {
	protected static Logger logger = LoggerFactory.getLogger(Identify.class);

	private String requestURL;

	public String response(Repository repository) {
		initializeRootElement();

		if (this.hasErrors()) {
			appendErrorNodes();
		} else {
			/*Repository repository;
			try {
				repository = RepositoryConnectionFactory.getRepository();
			} catch (RepositoryRegistrationException e) {
				e.printStackTrace();
				return null;
			}*/

			Element rootElement = xmlDocument.getDocumentElement();
			Element requestElement = xmlDocument.createElement("request");
			requestElement.setAttribute("verb", "Identify");
			requestElement.setTextContent(requestURL);
			rootElement.appendChild(requestElement);
			Element identifyElement = xmlDocument.createElement("Identify");
			Element repositoryNameElement = xmlDocument
					.createElement("repositoryName");
			repositoryNameElement
					.setTextContent(repository.getRepositoryName());
			identifyElement.appendChild(repositoryNameElement);
			Element baseUrlElement = xmlDocument.createElement("baseURL");
			baseUrlElement.setTextContent(repository.getRequestURL());
			identifyElement.appendChild(baseUrlElement);
			Element protocolVersionElement = xmlDocument
					.createElement("protocolVersion");
			protocolVersionElement.setTextContent(OAIPMH.VERSION);
			identifyElement.appendChild(protocolVersionElement);
			Element earliestDatestampElement = xmlDocument
					.createElement("earliestDatestamp");
			earliestDatestampElement.setTextContent(repository
					.getEarliestDatestamp());
			identifyElement.appendChild(earliestDatestampElement);
			Element deletedRecordElement = xmlDocument
					.createElement("deletedRecord");
			deletedRecordElement.setTextContent(repository.getDeletedRecord());
			identifyElement.appendChild(deletedRecordElement);
			Element granularityElement = xmlDocument
					.createElement("granularity");
			granularityElement.setTextContent(repository.getGranularity());
			identifyElement.appendChild(granularityElement);
			for (String email : repository.getAdminEmails()) {
				Element emailElement = xmlDocument.createElement("adminEmail");
				emailElement.setTextContent(email);
				identifyElement.appendChild(emailElement);
			}
			if (repository.getCompression() != null) {
				Element compressionElement = xmlDocument
						.createElement("compression");
				compressionElement.setTextContent(repository.getCompression());
				identifyElement.appendChild(compressionElement);
			}
			if (repository.getDescriptions() != null) {
				for (String descr : repository.getDescriptions()) {
					Element descriptionElement = xmlDocument
							.createElement("description");
					descriptionElement.setTextContent(descr);
					identifyElement.appendChild(descriptionElement);
				}
			}
			rootElement.appendChild(identifyElement);
		}
		try {
			return XMLUtils.transformDocumentToString(xmlDocument);
		} catch (XMLConversionException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void setAttributes(UriInfo req) {
		/*if (req.getParameterNames().hasMoreElements()) {
			if (!req.getParameterNames().nextElement().equals("verb")) {
				this.addError(new BadArgumentError());
			}
		}
		requestURL = req.getRequestURL().toString();*/
	}

}
