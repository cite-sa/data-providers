package gr.cite.oaipmh.verbs.errors;

public abstract class ErrorCondition extends Exception {
	abstract public String getMessage();

	abstract public String getCode();
}
