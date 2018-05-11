package gr.cite.opensearch.model.atom;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.URI;

@XmlRootElement(name = "content")
public class Content {
	
	private String type;
	private URI src;
	private String value;
	
    /*private String id;
    private String elementId;
    private String metadatumId;
    private Map<String, Object> mapProperty;
    private MapElements entry;*/
	
	@XmlAttribute(name = "type")
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlAttribute(name = "src")
	public URI getSrc() {
		return src;
	}
	
	public void setSrc(URI src) {
		this.src = src;
	}
	
	@XmlJavaTypeAdapter(MetadatumValueXmlAdapter.class)
	@XmlAnyElement
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/*//  @XmlJavaTypeAdapter(MapAdapter.class)
	public Map<String, Object> getMapProperty() {
		return mapProperty;
	}
	
	public void setMapProperty(Map<String, Object> map) {
		this.mapProperty = map;
	}
	
	@XmlElement
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement
	public String getElementId() {
		return elementId;
	}
	
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	
	@XmlElement
	public String getMetadatumId() {
		return metadatumId;
	}
	
	public void setMetadatumId(String metadatumId) {
		this.metadatumId = metadatumId;
	}
	
	@XmlElement(name = "entry")
	public MapElements getEntry() {
		return entry;
	}
	
	public void setEntry(MapElements entry) {
		this.entry = entry;
	}*/
}