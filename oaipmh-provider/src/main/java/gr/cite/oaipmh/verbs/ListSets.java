package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.repository.SetSpec;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.BadResumptionTokenError;
import gr.cite.oaipmh.verbs.errors.NoSetHierarchyError;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

public class ListSets extends Verb {
	protected static Logger logger = LoggerFactory.getLogger(ListSets.class);

	private String resumptionToken = null;
	private String requestURL;

	public String response() {
		initializeRootElement();
		List<SetSpec> setSpecs = null;
		Repository repository;
		try {
			repository = RepositoryConnectionFactory.getRepository();
		} catch (RepositoryRegistrationException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			if (resumptionToken == null) {
				setSpecs = repository.getSetSpecs();
			} else {
				setSpecs = repository.getSetSpecs(new ResumptionToken(
						resumptionToken));
			}
		} catch (NoSetHierarchyError e) {
			addError(e);
		} catch (BadResumptionTokenError e) {
			addError(e);
		}

		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setAttribute("verb", "ListSets");
		if (resumptionToken != null) {
			Verb.handleResumptionToken(requestElement, resumptionToken,
					setSpecs.size());
		}
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);

		if (this.hasErrors()) {
			appendErrorNodes();
		} else {
			Element listSetsElement = xmlDocument.createElement("ListSets");
			for (SetSpec setSpec : setSpecs) {
				try {
					listSetsElement.appendChild(xmlDocument.importNode(
							setSpec.getXMLElement(), true));
				} catch (DOMException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
			rootElement.appendChild(listSetsElement);
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
			if (paramSet.contains("resumptionToken")) {
				resumptionToken = req.getParameter("resumptionToken");
			} else {
				addError(new BadArgumentError());
			}
		}*/
	}

}
