package gr.cite.oaipmh.application.femme;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import gr.cite.earthserver.wcs.adaper.api.WCSAdapterAPI;
import gr.cite.earthserver.wcs.core.Coverage;
import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.metadata.OAIDCItem;
import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.repository.SetSpec;
import gr.cite.oaipmh.utils.UTCDatetime;
import gr.cite.oaipmh.verbs.errors.BadResumptionTokenError;
import gr.cite.oaipmh.verbs.errors.CannotDisseminateFormatError;
import gr.cite.oaipmh.verbs.errors.IdDoesNotExistError;
import gr.cite.oaipmh.verbs.errors.NoMetadataFormatsError;
import gr.cite.oaipmh.verbs.errors.NoRecordsMatchError;
import gr.cite.oaipmh.verbs.errors.NoSetHierarchyError;
import gr.cite.scarabaeus.utils.xml.XMLConverter;
import gr.cite.scarabaues.utils.xml.exceptions.XMLConversionException;

public class WCSRepository extends Repository {
	
	private static final Logger logger = LoggerFactory.getLogger(WCSRepository.class); 
	
	private WCSAdapterAPI wcsAdapter;
	
	
	@Inject
	public WCSRepository(WCSAdapterAPI wcsAdapter) {
		this.wcsAdapter = wcsAdapter;
	}

	@Override
	public List<Metadata> getMetadataFormats() throws NoMetadataFormatsError {
		List<Metadata> metaList = new ArrayList<>(1);
		metaList.add(new OAIDCItem());
		return metaList;
	}

	@Override
	public List<Metadata> getMetadataFormats(String identifier) throws NoMetadataFormatsError, IdDoesNotExistError {
		throw new NoMetadataFormatsError();
	}

	@Override
	public List<SetSpec> getSetSpecs() throws NoSetHierarchyError {
		List<SetSpec> setSpecs = new ArrayList<>();
		try {
			List<String> endpoints = wcsAdapter.getServers().stream().map(server -> server.getEndpoint()).collect(Collectors.toList());

			if (endpoints == null || endpoints.isEmpty()) {
				throw new NoSetHierarchyError();
			}

			for (String endpoint : endpoints) {
				setSpecs.add(new SetSpec(endpoint));
			}

			return setSpecs;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoSetHierarchyError();
		}
	}

	@Override
	public List<SetSpec> getSetSpecs(ResumptionToken resumptionToken)
			throws NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public Record getRecord(String id, String metadataPrefix) throws IdDoesNotExistError, CannotDisseminateFormatError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
			Coverage coverage = wcsAdapter.getCoverage(id);
			if (coverage == null) {
				throw new IdDoesNotExistError();
			}
			
			return coverageToRecord(coverage, metadataPrefix);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdDoesNotExistError();
		}
	}

	@Override
	public List<Record> getRecords(String metadataPrefix) throws CannotDisseminateFormatError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
			List<Coverage> coverages = wcsAdapter.getCoverages();

			if (coverages.size() < 1) {
				throw new IdDoesNotExistError();
			}
			
			List<Record> records = coverages.stream().map(new Function<Coverage, Record>() {

				@Override
				public Record apply(Coverage coverage) {
					try {
						return coverageToRecord(coverage, metadataPrefix);
					} catch (XMLConversionException e) {
						logger.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
				}
				
			}).collect(Collectors.toList());

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until, String metadataPrefix)
			throws CannotDisseminateFormatError, NoRecordsMatchError {
		throw new NoRecordsMatchError();
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, ResumptionToken resumptionToken)
			throws CannotDisseminateFormatError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, SetSpec set)
			throws CannotDisseminateFormatError, NoSetHierarchyError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
			List<Coverage> coverages = wcsAdapter.getCoverages(set.toString());

			if (coverages.size() < 1) {
				throw new IdDoesNotExistError();
			}
			
			List<Record> records = coverages.stream().map(new Function<Coverage, Record>() {

				@Override
				public Record apply(Coverage coverage) {
					try {
						return coverageToRecord(coverage, metadataPrefix);
					} catch (XMLConversionException e) {
						logger.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
				}
				
			}).collect(Collectors.toList());

			return records;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, ResumptionToken resumptionToken, SetSpec set)
			throws CannotDisseminateFormatError, NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until, String metadataPrefix,
			ResumptionToken resumptionToken, SetSpec set)
			throws CannotDisseminateFormatError, NoRecordsMatchError, NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}
	
	private Record coverageToRecord(Coverage coverage, String metadataPrefix) throws XMLConversionException {
		Record record = new Record(coverage.getId(), metadataPrefix);
		List<SetSpec> setSpecs = coverage.getServers().stream().map(server -> new SetSpec(server.getEndpoint()))
				.collect(Collectors.toList());
		record.setSetSpecs(setSpecs);
		record.setMetadata(new XSLTMetadataFromRepository((Element)XMLConverter.stringToNode(coverage.getMetadata(), true)));
		
		return record;
	}

}
