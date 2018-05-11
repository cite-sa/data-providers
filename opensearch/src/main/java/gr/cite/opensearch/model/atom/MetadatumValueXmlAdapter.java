package gr.cite.opensearch.model.atom;

import org.w3c.dom.Element;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class MetadatumValueXmlAdapter extends XmlAdapter<Element, String> {
	@Override
	public String unmarshal(Element doc) throws Exception {
		return null;
	}
	
	@Override
	public Element marshal(String xml) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))).getDocumentElement();
	}
}