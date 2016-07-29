package gr.cite.oaipmh.verbs.errors;

public class BadVerbError extends ErrorCondition {
	private final String code = "badVerb";

	public BadVerbError() {
		
	}
	
	public BadVerbError(String message) {
		
	}
	
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCode() {
		return code;
	}

}
