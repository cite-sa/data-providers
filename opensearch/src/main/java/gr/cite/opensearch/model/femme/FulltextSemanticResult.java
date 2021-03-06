package gr.cite.opensearch.model.femme;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FulltextSemanticResult {
	
	@JsonProperty("fulltext")
	private FulltextDocument fulltextResult;
	
	@JsonProperty("semantic")
	private List<FulltextDocument> semanticResults;
	
	public FulltextDocument getFulltextResult() {
		return fulltextResult;
	}
	
	public void setFulltextResult(FulltextDocument fulltextResult) {
		this.fulltextResult = fulltextResult;
	}
	
	public List<FulltextDocument> getSemanticResults() {
		return semanticResults;
	}
	
	public void setSemanticResults(List<FulltextDocument> semanticResults) {
		this.semanticResults = semanticResults;
	}
}
