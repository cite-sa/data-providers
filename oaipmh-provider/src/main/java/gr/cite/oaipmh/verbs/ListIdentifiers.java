package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.FlowControl;
import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.RepositoryConnectionFactory;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.repository.SetSpec;
import gr.cite.oaipmh.utils.UTCDatetime;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.BadResumptionTokenError;
import gr.cite.oaipmh.verbs.errors.CannotDisseminateFormatError;
import gr.cite.oaipmh.verbs.errors.NoMetadataFormatsError;
import gr.cite.oaipmh.verbs.errors.NoRecordsMatchError;
import gr.cite.oaipmh.verbs.errors.NoSetHierarchyError;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class ListIdentifiers extends Verb {
	protected static Logger logger = LoggerFactory.getLogger(ListIdentifiers.class);

	protected UTCDatetime from = null;
	protected UTCDatetime until = null;
	protected String metadataPrefix;
	protected SetSpec set = null;
	protected String resumptionToken = null;

	protected String requestURL;

	public String response(Repository repository) {
		initializeRootElement();
		List<Record> records;
		try {
			records = getRecords(repository);
		} catch (RepositoryRegistrationException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setAttribute("verb", "ListIdentifiers");
		requestElement.setAttribute("metadataPrefix", metadataPrefix);
		if (from != null) {
			requestElement.setAttribute("from", from.getDatetimeAsString());
		}
		if (until != null) {
			requestElement.setAttribute("until", until.getDatetimeAsString());
		}
		if (set != null) {
			requestElement.setAttribute("set", set.toString());
		}
		if (resumptionToken != null) {
			Verb.handleResumptionToken(requestElement, resumptionToken, records.size());
		}
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);

		if (this.hasErrors()) {
			appendErrorNodes();
		} else {
			Element listIdentifiersElement = xmlDocument.createElement("ListIdentifiers");
			for (Record record : records) {
				if (resumptionToken != null) {
					ResumptionToken resumptionToken = FlowControl.getInstance()
							.getResumptionToken(this.resumptionToken);
					if (resumptionToken.getExpirationDate().isBefore(record.getDatetime())) continue;
				}
				
				listIdentifiersElement.appendChild(xmlDocument.importNode(record.getHeaderAsXMLElement(), true));
			}

			rootElement.appendChild(listIdentifiersElement);
		}
		try {
			return XMLUtils.transformDocumentToString(xmlDocument);
		} catch (XMLConversionException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public void setAttributes(UriInfo req) {
		/*requestURL = req.getRequestURL().toString();*/
		requestURL = req.getAbsolutePath().toString();
		/*Set<String> parameters = new HashSet<String>(Collections.list(req.getParameterNames()));*/
		MultivaluedMap<String, String> parameters = new MultivaluedHashMap<>();
		for (Entry<String, List<String>> queryParam: req.getQueryParameters().entrySet()) {
			for (String queryParamValue: queryParam.getValue()) {
				parameters.add(queryParam.getKey(), queryParamValue);				
			}
		}
		if (parameters.containsKey("from")) {
			from = new UTCDatetime(parameters.getFirst("from"));
			parameters.remove("from");
		}
		if (parameters.containsKey("until")) {
			until = new UTCDatetime(parameters.getFirst("until"));
			parameters.remove("until");
		}
		if (parameters.containsKey("metadataPrefix")) {
			metadataPrefix = parameters.getFirst("metadataPrefix");
			parameters.remove("metadataPrefix");
		} else {
			addError(new BadArgumentError());
		}
		if (parameters.containsKey("set")) {
			set = new SetSpec(parameters.getFirst("set"));
			parameters.remove("set");
		}
		if (parameters.containsKey("resumptionToken")) {
			resumptionToken = parameters.getFirst("resumptionToken");
			parameters.remove("resumptionToken");
		}
		parameters.remove("verb");
		if (parameters.size() > 0) {
			addError(new BadArgumentError());
		}
	}

	protected List<Record> getRecords(Repository repository) throws RepositoryRegistrationException {

		try {
			if (until == null && from == null && set == null && resumptionToken == null) {
				return repository.getRecords(metadataPrefix);
			} else if (until != null || from != null && set == null && resumptionToken == null) {
				return repository.getRecords(from, until, metadataPrefix);
			} else if (until == null && from == null && set != null	&& resumptionToken == null) {
				return repository.getRecords(metadataPrefix, set);
			} else if (until == null && from == null && set == null && resumptionToken != null) {
				return repository.getRecords(metadataPrefix, new ResumptionToken(resumptionToken));
			} else if (until == null && from == null && set != null && resumptionToken != null) {
				return repository.getRecords(metadataPrefix, new ResumptionToken(resumptionToken), set);
			} else {
				return repository.getRecords(from, until, metadataPrefix, new ResumptionToken(resumptionToken), set);
			}
		} catch (CannotDisseminateFormatError | NoRecordsMatchError | NoSetHierarchyError | BadResumptionTokenError e) {
			addError(e);
			logger.info("ErrorCondition: " + e.getCode());
		}
		return null;
	}
}
