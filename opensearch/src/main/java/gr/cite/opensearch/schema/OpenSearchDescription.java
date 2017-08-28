package gr.cite.opensearch.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shortName",
    "description",
    "url",
    "contact",
    "tags",
    "longName",
    "image",
    "query",
    "developer",
    "attribution",
    "syndicationRight",
    "adultContent",
    "language",
    "inputEncoding",
    "outputEncoding"
})

@XmlRootElement(name = "OpenSearchDescription")
public class OpenSearchDescription {

    @XmlElement(name = "ShortName", required = true)
    private String shortName;
    @XmlElement(name = "Description", required = true)
    private String description;
    @XmlElement(name = "Url", required = true)
    private List<Url> url;
    @XmlElement(name = "Contact")
    private String contact;
    @XmlElement(name = "Tags")
    private String tags;
    @XmlElement(name = "LongName")
    private String longName;
    @XmlElement(name = "Image")
    private ImageType image;
    @XmlElement(name = "Query")
    private List<Object> query;
    @XmlElement(name = "Developer")
    private String developer;
    @XmlElement(name = "Attribution")
    private String attribution;
    @XmlElement(name = "SyndicationRight", defaultValue = "open")
    private String syndicationRight;
    @XmlElement(name = "AdultContent", defaultValue = "false")
    private String adultContent;
    @XmlElement(name = "Language", defaultValue = "*")
    private List<String> language;
    @XmlElement(name = "InputEncoding", defaultValue = "UTF-8")
    private String inputEncoding;
    @XmlElement(name = "OutputEncoding", defaultValue = "UTF-8")
    private String outputEncoding;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String value) {
        this.shortName = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public List<Url> getUrl() {
        if (url == null) {
            url = new ArrayList<>();
        }
        return this.url;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String value) {
        this.contact = value;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String value) {
        this.tags = value;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String value) {
        this.longName = value;
    }

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType value) {
        this.image = value;
    }

    public List<Object> getQuery() {
        if (query == null) {
            query = new ArrayList<>();
        }
        return this.query;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String value) {
        this.developer = value;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String value) {
        this.attribution = value;
    }

    public String getSyndicationRight() {
        return syndicationRight;
    }

    public void setSyndicationRight(String value) {
        this.syndicationRight = value;
    }

    public String getAdultContent() {
        return adultContent;
    }

    public void setAdultContent(String value) {
        this.adultContent = value;
    }

    public List<String> getLanguage() {
        if (language == null) {
            language = new ArrayList<>();
        }
        return this.language;
    }

    public String getInputEncoding() {
        return inputEncoding;
    }

    public void setInputEncoding(String value) {
        this.inputEncoding = value;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String value) {
        this.outputEncoding = value;
    }

}
