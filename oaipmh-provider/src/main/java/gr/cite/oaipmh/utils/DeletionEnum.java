package gr.cite.oaipmh.utils;

/**
 * 
 * @author Ioannis Kavvouras
 *
 */
public enum DeletionEnum {
	NO("no"), PERSISTENT("persistent"), TRANSIENT("transient");
	private final String name;

	private DeletionEnum(String name) {
		this.name = name;
	}

	/**
	 * get the {@link DeletionEnum} value as string
	 */
	@Override
	public String toString() {
		return name;
	}
}
