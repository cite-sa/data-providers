package gr.cite.oaipmh.utils;

import gr.cite.oaipmh.verbs.errors.ErrorCondition;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLUtils {
	public static final String XMLSCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";

	private XMLUtils() {
	}

	public static Element errorToXML(ErrorCondition error, Document document) {
		Element element = document.createElement("error");
		element.setAttribute("code", error.getCode());
		if ((error.getMessage() != null) && (!error.getMessage().isEmpty())) {
			element.setTextContent(error.getMessage());
		}
		return element;
	}

	public static String transformDocumentToString(Document document) throws XMLConversionException {
		return XMLConverter.nodeToString(document);
	}
	
	public static String transformDocumentToString(Element document) throws XMLConversionException {
		return XMLConverter.nodeToString(document);
	}
}
