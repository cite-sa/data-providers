package gr.cite.oaipmh.verbs;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.BadVerbError;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

public class VerbError extends Verb {
	protected static Logger logger = LoggerFactory.getLogger(VerbError.class);
	
	protected String requestURL;
	
	@Override
	public String response(Repository repository) {
		initializeRootElement();
		
		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);

		if (this.hasErrors()) {
			appendErrorNodes();
		}
		
		try {
			return XMLUtils.transformDocumentToString(xmlDocument);
		} catch (XMLConversionException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void setAttributes(UriInfo req) {
		
		requestURL = req.getAbsolutePath().toString();
		addError(new BadVerbError(req.getQueryParameters().getFirst("verb")));
		
	}

}
