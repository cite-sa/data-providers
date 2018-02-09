package gr.cite.opensearch.model.elements;

import gr.cite.opensearch.model.opensearch.Query;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Channel{
    private String title;

    private List<Link> link;

    private String description;

    private List<Item> item;

    private Query query;

    private int totalResults;

    private int startIndex;

    private int startPage;

    private int itemPerPage;

    @XmlElement(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement(name = "link")
    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> link) {
        this.link = link;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "item")
    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "Query")
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "totalResults")
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "startIndex")
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "startPage")
    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "itemPerPage")
    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }
}
