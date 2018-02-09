package gr.cite.opensearch.model.opensearch;

import gr.cite.opensearch.model.elements.Entry;
import gr.cite.opensearch.model.opensearch.Query;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

public class OpenSearchResponse {

    protected Query query;

    protected int totalResults;
    protected int count;

    protected int startIndex;

    protected int startPage;

    protected String language;

    protected String inputEncoding;

    protected String outputEncoding;



    @XmlElement(namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "Query")
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
    @XmlElement( namespace = "http://a9.com/-/spec/opensearch/1.1/", name = "totalResults")
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @XmlElement(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @XmlElement(name = "startIndex")
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @XmlElement(name = "startPage")
    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    @XmlElement(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @XmlElement(name = "inputEncoding")
    public String getInputEncoding() {
        return inputEncoding;
    }

    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    @XmlElement(name = "outputEncoding")
    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }


}
