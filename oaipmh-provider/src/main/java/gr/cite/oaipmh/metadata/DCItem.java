package gr.cite.oaipmh.metadata;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gr.cite.oaipmh.utils.XMLUtils;

/**
 * General Dublin Core Item {@link Metadata metadata}
 * @author Ioannis Kavvouras
 *
 */
public class DCItem extends Metadata {
	/**
	 * the namespace of Dublin Core: <a href='http://purl.org/dc/elements/1.1/'>http://purl.org/dc/elements/1.1/</a>
	 */
	public static final String DC_NAMESPACE = "http://purl.org/dc/elements/1.1/";

	public DCItem(String prefix, String schemaLocation, String namespace) {
		super(prefix, schemaLocation, namespace);
	}

	private String title;
	private String creator;
	private String subject;
	private String description;
	private String publisher;
	private String contributor;
	private String date;
	private String type;
	private String format;
	private String identifier;
	private String source;
	private String language;
	private String relation;
	private String coverage;
	private String rights;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	@Override
	public Element getXMLElement() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDocument = builder.newDocument();
		Element rootElement = xmlDocument.createElementNS(this.getNamespace(),
				getPrefix() + ":dc");
		rootElement.setAttributeNS(XMLUtils.XMLSCHEMA_INSTANCE,
				"xsi:schemaLocation",
				this.getNamespace() + " " + this.getSchema());

		rootElement.setAttribute("xmlns:dc", DC_NAMESPACE);

		createDCElement(rootElement, "title", title, xmlDocument);
		createDCElement(rootElement, "creator", creator, xmlDocument);
		createDCElement(rootElement, "subject", subject, xmlDocument);
		createDCElement(rootElement, "description", description, xmlDocument);
		createDCElement(rootElement, "publisher", publisher, xmlDocument);
		createDCElement(rootElement, "dontributor", contributor, xmlDocument);
		createDCElement(rootElement, "date", date, xmlDocument);
		createDCElement(rootElement, "type", type, xmlDocument);
		createDCElement(rootElement, "format", format, xmlDocument);
		createDCElement(rootElement, "identifier", identifier, xmlDocument);
		createDCElement(rootElement, "source", source, xmlDocument);
		createDCElement(rootElement, "language", language, xmlDocument);
		createDCElement(rootElement, "relation", relation, xmlDocument);
		createDCElement(rootElement, "coverage", coverage, xmlDocument);
		createDCElement(rootElement, "rights", rights, xmlDocument);

		return rootElement;
	}

	private static void createDCElement(Element rootElement, String name,
			String value, Document document) {
		if (value != null) {
			Element element = document.createElementNS(DC_NAMESPACE, "dc:"
					+ name);
			element.setTextContent(value);
			rootElement.appendChild(element);
		}
	}

}
