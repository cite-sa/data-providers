package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.repository.FlowControl;
import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.RepositoryRegistrationException;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.utils.XMLUtils;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import java.util.List;

import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class ListRecords extends ListIdentifiers {
	protected static Logger logger = LoggerFactory.getLogger(ListRecords.class);

	public String response() {
		initializeRootElement();
		List<Record> records;
		try {
			records = getRecords();
		} catch (RepositoryRegistrationException e1) {
			e1.printStackTrace();
			return null;
		}

		Element rootElement = xmlDocument.getDocumentElement();
		Element requestElement = xmlDocument.createElement("request");
		requestElement.setAttribute("verb", "ListRecords");
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
			Verb.handleResumptionToken(requestElement, resumptionToken,
					records.size());
		}
		requestElement.setTextContent(requestURL);
		rootElement.appendChild(requestElement);

		if (this.hasErrors()) {
			appendErrorNodes();
		} else {
			Element listIdentifiersElement = xmlDocument
					.createElement("ListRecords");
			for (Record record : records) {
				if (resumptionToken != null) {
					ResumptionToken resumptionToken = FlowControl.getInstance()
							.getResumptionToken(this.resumptionToken);
					if (resumptionToken.getExpirationDate().isBefore(
							record.getDatetime())) {
						continue;
					}
				}
				listIdentifiersElement.appendChild(xmlDocument.importNode(
						record.getXMLElement(), true));
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

}
