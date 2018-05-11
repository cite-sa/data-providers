package gr.cite.opensearch.model.atom;

import gr.cite.opensearch.model.rss.Link;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "title", "updated", "content", "link", "summary", "box"})
public class Entry {
	
	private String id;
	private String title;
	private String updated;
	private Content content;
	
	private Link link;
	private String summary;
	private String box;
	
	@XmlElement(name = "georss:line")
	public String getBox() {
		return box;
	}
	
	public void setBox(String box) {
		this.box = box;
	}
	
	@XmlElement(name = "id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
