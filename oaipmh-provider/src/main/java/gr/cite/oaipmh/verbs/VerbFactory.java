package gr.cite.oaipmh.verbs;

import gr.cite.oaipmh.verbs.errors.BadVerbError;

public class VerbFactory {
	public static class Verbs {
		static final String GET_RECORD = "GetRecord";
		static final String IDENTIFY = "Identify";
		static final String LIST_IDENTIFIERS = "ListIdentifiers";
		static final String LIST_METADATA_FORMATS = "ListMetadataFormats";
		static final String LIST_RECORDS = "ListRecords";
		static final String LIST_SETS = "ListSets";
	}

	private VerbFactory() {

	}

	public static Verb getVerbFactoryMethod(String verb) throws BadVerbError {
		if (verb == null) {
			throw new BadVerbError();
		}

		switch (verb) {
			case Verbs.GET_RECORD:
				return new GetRecord();
			case Verbs.IDENTIFY:
				return new Identify();
			case Verbs.LIST_IDENTIFIERS:
				return new ListIdentifiers();
			case Verbs.LIST_METADATA_FORMATS:
				return new ListMetadataFormats();
			case Verbs.LIST_RECORDS:
				return new ListRecords();
			case Verbs.LIST_SETS:
				return new ListSets();
			default:
				throw new BadVerbError();
		}
	}
}
