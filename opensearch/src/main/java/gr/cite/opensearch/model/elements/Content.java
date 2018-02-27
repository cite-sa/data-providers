package gr.cite.opensearch.model.elements;

import gr.cite.opensearch.utils.MapAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

@XmlRootElement(name="content")
public class Content {

    private String type;
    private String id;
    private String elementId;
    private String metadatumId;


    private Map<String, Object> mapProperty;

    private MapElements entry;

  //  @XmlJavaTypeAdapter(MapAdapter.class)
  public Map<String, Object> getMapProperty() {
        return mapProperty;
    }

    public void setMapProperty(Map<String, Object> map) {
        this.mapProperty = map;
    }
    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    }
}