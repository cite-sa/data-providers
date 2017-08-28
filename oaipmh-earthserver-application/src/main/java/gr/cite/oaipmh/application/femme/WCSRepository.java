package gr.cite.oaipmh.application.femme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import gr.cite.femme.client.api.FemmeClientAPI;
import gr.cite.femme.core.model.Collection;
import gr.cite.femme.core.model.DataElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.metadata.OaiDcItem;
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

	private FemmeClientAPI femmeClient;
	
	@Inject
	public WCSRepository(FemmeClientAPI femmeClient) {
		this.femmeClient = femmeClient;
	}

	@Override
	public List<Metadata> getMetadataFormats() throws NoMetadataFormatsError {
		List<Metadata> metaList = new ArrayList<>(1);
		metaList.add(new OaiDcItem());
		return metaList;
	}

	@Override
	public List<Metadata> getMetadataFormats(String identifier) throws NoMetadataFormatsError, IdDoesNotExistError {
		throw new NoMetadataFormatsError();
	}

	@Override
	public List<SetSpec> getSetSpecs() throws NoSetHierarchyError {
		try {
			List<Collection> endpoints = this.femmeClient.getCollections();

			if (endpoints == null || endpoints.isEmpty()) {
				throw new NoSetHierarchyError();
			}

			return endpoints.stream().map(collection -> new SetSpec(
					"organization-" + collection.getName().toLowerCase().replaceAll(" ", "-"),
					collection.getName()
				)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoSetHierarchyError();
		}
	}

	@Override
	public List<SetSpec> getSetSpecs(ResumptionToken resumptionToken) throws NoSetHierarchyError, BadResumptionTokenError {
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
			DataElement dataElement = this.femmeClient.getDataElementById(id);
			if (dataElement == null) {
				throw new IdDoesNotExistError();
			}
			
			return dataElementToRecord(dataElement, metadataPrefix);
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
		} catch (NoMetadataFormatsError e) {
			throw new CannotDisseminateFormatError();
		}
		try {
			List<DataElement> dataElements = this.femmeClient.getDataElements();

			if (dataElements.size() < 1) {
				throw new IdDoesNotExistError();
			}

			return dataElements.stream().map(dataElement -> {
				try {
					return dataElementToRecord(dataElement, metadataPrefix);
				} catch (XMLConversionException e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until, String metadataPrefix) throws CannotDisseminateFormatError, NoRecordsMatchError {
		throw new NoRecordsMatchError();
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, ResumptionToken resumptionToken) throws CannotDisseminateFormatError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, SetSpec set) throws CannotDisseminateFormatError, NoSetHierarchyError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
			List<DataElement> coverages = this.femmeClient.getDataElementsInCollectionByName(set.toString());

			if (coverages.size() < 1) {
				throw new IdDoesNotExistError();
			}
			
			return coverages.stream().map(coverage -> {
				try {
					return dataElementToRecord(coverage, metadataPrefix);
				} catch (XMLConversionException e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				
			}).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(String metadataPrefix, ResumptionToken resumptionToken, SetSpec set) throws CannotDisseminateFormatError, NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until, String metadataPrefix, ResumptionToken resumptionToken, SetSpec set)
			throws CannotDisseminateFormatError, NoRecordsMatchError, NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}
	
	private Record dataElementToRecord(DataElement dataElement, String metadataPrefix) throws XMLConversionException {
		Record record = new Record(dataElement.getId(), metadataPrefix);
		List<SetSpec> setSpecs = dataElement.getCollections().stream().map(server ->
				new SetSpec("organization-" + server.getName().toLowerCase().replaceAll(" ", "-")))
			.collect(Collectors.toList());
		record.setSetSpecs(setSpecs);
		if (dataElement.getMetadata() != null && !dataElement.getMetadata().isEmpty()) {
			record.setMetadata(new XSLTMetadataFromRepository((Element) XMLConverter.stringToNode(dataElement.getMetadata().get(0).getValue(), true)));
		}
		
		return record;
	}

}
