package gr.cite.oaipmh.repository;

import gr.cite.oaipmh.utils.UTCDatetime;

/**
 * 
 * @author Ioannis Kavvouras
 *
 */
public class ResumptionToken {
	private final String resumptionToken;
	private int completeListSize;
	private UTCDatetime expirationDate;
	private int cursor;

	public ResumptionToken(String resumptionToken) {
		this.resumptionToken = resumptionToken;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}

	public int getCursor() {
		return cursor;
	}

	public void setExpirationDate(UTCDatetime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public UTCDatetime getExpirationDate() {
		return expirationDate;
	}

	public void setCompleteListSize(int completeListSize) {
		this.completeListSize = completeListSize;
	}

	public int getCompleteListSize() {
		return completeListSize;
	}

	/**
	 * returns the 'resumptionToken'
	 */
	@Override
	public String toString() {
		return resumptionToken;
	}
}
