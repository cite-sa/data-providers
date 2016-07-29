package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.IdDoesNotExistError;
import gr.cite.oaipmh.verbs.errors.NoMetadataFormatsError;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class ListMetadataFormats extends Verb {
	protected static Logger logger = LoggerFactory.getLogger(ListMetadataFormats.class);

	private String identifier = null;
	private String requestURL;

	public String response() {
		initializeRootElement();
		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setAttribute("verb", "ListMetadataFormats");
		if (identifier != null) {
			requestElement.setAttribute("identifier", identifier);
		}
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);
		Repository repository;
		try {
			repository = RepositoryConnectionFactory.getRepository();
		} catch (RepositoryRegistrationException e1) {
			e1.printStackTrace();
			return null;
		}
		List<Metadata> metadataFormats = null;
		try {
			if (identifier == null) {
				metadataFormats = repository.getMetadataFormats();
			} else {
				metadataFormats = repository.getMetadataFormats(identifier);
			}
		} catch (NoMetadataFormatsError e) {
			addError(e);
		} catch (IdDoesNotExistError e) {
			addError(e);
		}
		if (this.hasErrors()) {
			appendErrorNodes();
		} else {

			Element listMetadataFormatsElement = xmlDocument
					.createElement("ListMetadataFormats");
			for (Metadata metadataFormat : metadataFormats) {
				listMetadataFormatsElement.appendChild(xmlDocument.importNode(
						metadataFormat.getFormatXMLElement(), true));
			}
			rootElement.appendChild(listMetadataFormatsElement);
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
		Set<String> paramSet = new HashSet<String>(Collections.list(req
				.getParameterNames()));
		paramSet.remove("verb");
		if (!paramSet.isEmpty()) {
			if (paramSet.contains("identifier")) {
				identifier = req.getParameter("identifier");
			} else {
				addError(new BadArgumentError());
			}
		}*/
	}

}
