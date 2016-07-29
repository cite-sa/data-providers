package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.verbs.errors.BadArgumentError;
import gr.cite.oaipmh.verbs.errors.BadVerbError;

/**
 * 
 * @author Ioannis Kavvouras
 *
 */
public class VerbFactory {
	private VerbFactory() {

	}

	public static Verb getVerbFactoryMethod(String verb) throws BadVerbError {
		if (verb == null) {
			throw new BadVerbError();
		}
		if (verb.equals("GetRecord")) {
			return new GetRecord();
		} else if (verb.equals("Identify")) {
			return new Identify();
		} else if (verb.equals("ListIdentifiers")) {
			return new ListIdentifiers();
		} else if (verb.equals("ListMetadataFormats")) {
			return new ListMetadataFormats();
		} else if (verb.equals("ListRecords")) {
			return new ListRecords();
		} else if (verb.equals("ListSets")) {
			return new ListSets();
		} else {
			throw new BadVerbError();
		}
	}
}
