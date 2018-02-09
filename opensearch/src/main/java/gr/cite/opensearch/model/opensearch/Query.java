package gr.cite.opensearch.model.opensearch;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

public class Query {

    public enum RoleValue {
        REQUEST("request"),
        EXAMPLE("example"),
        RELATED("related"),
        CORRECTION("correction"),
        SUBSET("subset"),
        SUPERSET("superset");

        private final String roleValue;

        private RoleValue(final String roleValue) {
            this.roleValue = roleValue;
        }

        public String getRoleValue() {
            return this.roleValue;
        }
    }

    private String role;
    private String title;

    private BigInteger totalResults;

    private String searchTerms;

    private BigInteger count;

    private BigInteger startIndex;

    private BigInteger startPage;

    private String language;

    private String inputEncoding;

    private String outputEncoding;

    @XmlAttribute(name = "role", required = true)
    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        this.role = value;
    }

    @XmlAttribute(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @XmlAttribute(name = "totalResults")
    public BigInteger getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(BigInteger value) {
        this.totalResults = value;
    }

    @XmlAttribute(name = "searchTerms")
    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String value) {
        this.searchTerms = value;
    }

    @XmlAttribute(name = "count")
    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger value) {
        this.count = value;
    }
    @XmlAttribute(name = "startIndex")
    public BigInteger getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(BigInteger value) {
        this.startIndex = value;
    }
    @XmlAttribute(name = "startPage")
    public BigInteger getStartPage() {
        return startPage;
    }

    public void setStartPage(BigInteger value) {
        this.startPage = value;
    }

    @XmlAttribute(name = "language")
    public String getLanguage() {
        if (language == null) {
            return "*";
        } else {
            return language;
        }
    }

    public void setLanguage(String value) {
        this.language = value;
    }
    @XmlAttribute(name = "inputEncoding")
    public String getInputEncoding() {
        if (inputEncoding == null) {
            return "UTF-8";
        } else {
            return inputEncoding;
        }
    }

    public void setInputEncoding(String value) {
        this.inputEncoding = value;
    }
    @XmlAttribute(name = "outputEncoding")
    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String value) {
        this.outputEncoding = value;
    }

}
