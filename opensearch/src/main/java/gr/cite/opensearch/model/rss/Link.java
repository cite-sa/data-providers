package gr.cite.opensearch.model.elements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;


public class Link {

    private String rel;

    private String href;
    private String type;

    private String title;
    private String hreflang;

    private BigInteger length;
    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @XmlAttribute(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlAttribute(name = "hreflang")
    public String getHreflang() {
        return hreflang;
    }


    public void setHreflang(String hreflang) {
        this.hreflang = hreflang;
    }
    @XmlAttribute(name = "length")
    public BigInteger getLength() {
        return length;
    }

    public void setLength(BigInteger length) {
        this.length = length;
    }
    @XmlAttribute(name = "rel")
    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    @XmlAttribute
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
