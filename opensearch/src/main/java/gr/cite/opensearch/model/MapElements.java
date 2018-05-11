package gr.cite.opensearch.model.elements;

import javax.xml.bind.annotation.XmlElement;

public class MapElements {
    @XmlElement
    public String  key;
    @XmlElement(name = "value")
    public Object value;

    private MapElements() {} //Required by JAXB

    public MapElements(String key, Object value)
    {
        this.key   = key;
        this.value = value;
    }


}
