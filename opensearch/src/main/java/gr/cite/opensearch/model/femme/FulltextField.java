package gr.cite.opensearch.model.femme;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FulltextField {
	
	@JsonProperty("field")
	private String field;
	
	@JsonProperty("value")
	private String value;
	
	public FulltextField(String field, String value) {
		this.field = field;
		this.value = value;
	}
	
	public String getField() {
		return this.field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}