package gr.cite.opensearch.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryType")
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

    @XmlAttribute(name = "role", required = true)
    private String role;
    @XmlAttribute(name = "title")
    private String title;
    @XmlAttribute(name = "totalResults")
    private BigInteger totalResults;
    @XmlAttribute(name = "searchTerms")
    private String searchTerms;
    @XmlAttribute(name = "count")
    private BigInteger count;
    @XmlAttribute(name = "startIndex")
    private BigInteger startIndex;
    @XmlAttribute(name = "startPage")
    private BigInteger startPage;
    @XmlAttribute(name = "language")
    @XmlSchemaType(name = "anySimpleType")
    private String language;
    @XmlAttribute(name = "inputEncoding")
    private String inputEncoding;
    @XmlAttribute(name = "outputEncoding")
    private String outputEncoding;

    public String getRole() {
        return role;
    }

    public void setRole(String value) {
        this.role = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public BigInteger getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(BigInteger value) {
        this.totalResults = value;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String value) {
        this.searchTerms = value;
    }

    public BigInteger getCount() {
        return count;
    }

    public void setCount(BigInteger value) {
        this.count = value;
    }

    public BigInteger getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(BigInteger value) {
        this.startIndex = value;
    }

    public BigInteger getStartPage() {
        return startPage;
    }

    public void setStartPage(BigInteger value) {
        this.startPage = value;
    }

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

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String value) {
        this.outputEncoding = value;
    }

}
