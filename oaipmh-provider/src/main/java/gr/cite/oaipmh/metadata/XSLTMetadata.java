package gr.cite.oaipmh.metadata;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gr.cite.oaipmh.utils.OAIPMH;

/**
 * An XML record transformed to {@link Metadata} by an XSL transformation
 * 
 * @author Ioannis Kavvouras
 * 
 */
public class XSLTMetadata extends Metadata {

	private final Element elementToTransform;
	private final String XSLTfilename;

	/**
	 * 
	 * @param elementToTransform
	 *            the {@link Element} that will be transformed using an XSL
	 *            transformation
	 * @param XSLTfilename
	 *            the path of the filename that contains the xsl transformation.
	 */
	public XSLTMetadata(Element elementToTransform, String XSLTfilename) {
		super("oai_dc", OAIPMH.OAI_SCHEMA_LOCATION, OAIPMH.OAI_NAMESPACE);

		this.elementToTransform = elementToTransform;
		this.XSLTfilename = XSLTfilename;
	}

	@Override
	public Element getXMLElement() throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource(new File(XSLTfilename));
		try {
			Transformer transformer = factory.newTransformer(xslt);
			Document element = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			DOMResult result = new DOMResult(element);
			transformer.transform(new DOMSource(elementToTransform), result);
			return element.getDocumentElement();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			throw e;
		} catch (TransformerException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
