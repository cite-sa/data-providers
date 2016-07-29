package gr.cite.oaipmh.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * the flow control that handles the {@link ResumptionToken resumptionTokens}
 * @author Ioannis Kavvouras
 *
 */
public class FlowControl {
	private static FlowControl instance = null;

	private Map<String, ResumptionToken> resumptionTokens;
	
	private FlowControl() {
		resumptionTokens = new HashMap<String, ResumptionToken>();
	}
	
	public static FlowControl getInstance() {
		if (instance == null) {
			instance = new FlowControl();
		}
		return instance;
	}
	
	public void register(ResumptionToken resumptionToken) {
		resumptionTokens.put(resumptionToken.toString(), resumptionToken);
	}
	
	public void unregister(ResumptionToken resumptionToken) {
		resumptionTokens.remove(resumptionToken.toString());
	}
	
	public boolean contains(ResumptionToken resumptionToken) {
		return resumptionTokens.containsKey(resumptionToken.toString());
	}
	
	public ResumptionToken getResumptionToken(String resumptionToken) {
		return resumptionTokens.get(resumptionToken);
	}

}
