package gr.cite.opensearch.model.elements;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="entry")
public class Entry {

    private int id;
    private String title;
    private String updated;
    private Content content;

    private Link link;
    private String summary;

    @XmlElement(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "updated")
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
    @XmlElement(name = "content")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    @XmlElement(name = "link")
    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
    @XmlElement(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }






}
