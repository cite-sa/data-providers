package gr.cite.opensearch.model.atom;

import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.model.rss.Link;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "feed")
@XmlType(propOrder = {
	"title",
	"link",
	"updated",
	"author",
	"totalResults",
	"startIndex",
	"startPage",
	"query",
	"inputEncoding",
	"outputEncoding",
	"language",
	"count",
	"entries"
})
public class OpenSearchResponseAtom extends OpenSearchResponse {
	
	private String title;
	private List<Link> link;
	private String updated;
	private Author author;
	private List<Entry> entries;
	private Query query;
	private int totalResults;
	private int count;
	private int startIndex;
	private int startPage;
	private String language;
	private String inputEncoding;
	private String outputEncoding;
	
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
	
	@XmlAttribute(name = "title")
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
	
	@XmlAttribute(name = "updated")
	public String getUpdated() {
		return updated;
	}
	
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	@XmlElement(name = "author")
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	@XmlElement(name = "entry")
	public List<Entry> getEntries() {
		return entries;
	}
	
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "author")
	private static class Author {
		
		@XmlElement(name = "name")
		private String name;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
}
