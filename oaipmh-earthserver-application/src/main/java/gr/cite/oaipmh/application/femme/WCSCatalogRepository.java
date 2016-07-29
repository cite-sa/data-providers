/*package gr.cite.oaipmh.application.femme;

import gr.cite.femme.client.FemmeClient;
import gr.cite.oaipmh.metadata.Metadata;
import gr.cite.oaipmh.metadata.OAIDCItem;
import gr.cite.oaipmh.metadata.XSLTMetadata;
import gr.cite.oaipmh.repository.Record;
import gr.cite.oaipmh.repository.Repository;
import gr.cite.oaipmh.repository.ResumptionToken;
import gr.cite.oaipmh.repository.SetSpec;
import gr.cite.oaipmh.servlet.Utils;
import gr.cite.oaipmh.utils.DeletionEnum;
import gr.cite.oaipmh.utils.UTCDatetime;
import gr.cite.oaipmh.verbs.errors.BadResumptionTokenError;
import gr.cite.oaipmh.verbs.errors.CannotDisseminateFormatError;
import gr.cite.oaipmh.verbs.errors.IdDoesNotExistError;
import gr.cite.oaipmh.verbs.errors.NoMetadataFormatsError;
import gr.cite.oaipmh.verbs.errors.NoRecordsMatchError;
import gr.cite.oaipmh.verbs.errors.NoSetHierarchyError;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class WCSCatalogRepository extends Repository {

	public WCSCatalogRepository() throws Exception {

		wcsCatalogManager = WCSCatalogManager.getManager();

		setAdminEmails(new ArrayList<String>());
		setDeletedRecord(DeletionEnum.PERSISTENT);
		setRepositoryName("wcs");
		try {
			setRequestURL(wcsCatalogManager.getCatalogURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Metadata> getMetadataFormats() throws NoMetadataFormatsError {
		List<Metadata> metaList = new ArrayList<>(1);
		metaList.add(new OAIDCItem());
		return metaList;
	}

	@Override
	public List<Metadata> getMetadataFormats(String identifier)
			throws NoMetadataFormatsError, IdDoesNotExistError {
		throw new NoMetadataFormatsError();
	}

	@Override
	public List<SetSpec> getSetSpecs() throws NoSetHierarchyError {
		List<SetSpec> setSpecs = new ArrayList<>();

		try {
			List<String> endpoints = wcsCatalogManager
					.executeXQueryOnCollectionResources("for $x in /server/@endpoint return data($x)");

			if (endpoints == null || endpoints.isEmpty()) {
				throw new NoSetHierarchyError();
			}

			for (String endpoint : endpoints) {
				setSpecs.add(new SetSpec(endpoint));
			}

			return setSpecs;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSetHierarchyError();
		}
	}

	@Override
	public List<SetSpec> getSetSpecs(ResumptionToken resumptionToken)
			throws NoSetHierarchyError, BadResumptionTokenError {
		throw new BadResumptionTokenError();
	}

	@Override
	public Record getRecord(String id, String metadataPrefix)
			throws IdDoesNotExistError, CannotDisseminateFormatError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
//			String xquery = "let $xslt-stylesheet := doc('wcs-collection-config/coverage-dc-xslt.xsl') "
//					+ "let $cov := /server/formal/lastimage/harvested/coverages/coverage[@id='"
//					+ id
//					+ "'] "
//					+ "return xslt:transform($cov, $xslt-stylesheet)";
			String xquery = createTransformationQuery(null, id);
			
			List<String> coverages = wcsCatalogManager
					.executeXQueryOnCollectionResources(xquery);

			if (coverages.isEmpty()) {
				throw new IdDoesNotExistError();
			}
			Document document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(new InputSource(new StringReader(coverages.get(0))));
			Record record = new Record(id, metadataPrefix);
			record.setMetadata(new XSLTMetadataFromRepository(document
					.getDocumentElement()));
			List<String> active = wcsCatalogManager
					.executeXQueryOnCollectionResources("data(/server/formal/lastimage/harvested/coverages/coverage[@id='"
							+ id + "']/@active)");
			if (!active.isEmpty()) {
				record.setDeleted(!new Boolean(active.get(0)));
			}
			return record;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IdDoesNotExistError();
		}
	}

	@Override
	public List<Record> getRecords(String metadataPrefix)
			throws CannotDisseminateFormatError {
		try {
			if (!getMetadataFormats().get(0).getPrefix().equals(metadataPrefix)) {
				throw new CannotDisseminateFormatError();
			}
		} catch (NoMetadataFormatsError e1) {
			throw new CannotDisseminateFormatError();
		}
		try {
//			String query = "let $xslt-stylesheet := doc('wcs-collection-config/coverage-dc-xslt.xsl') "
//					+ "for $cov in /server/formal/lastimage/harvested/coverages/coverage "
//					+ "return xslt:transform($cov, $xslt-stylesheet)";
			String query = createTransformationQuery(null, null);
			List<String> coverages = wcsCatalogManager
					.executeXQueryOnCollectionResources(query);

			if (coverages.size() < 1) {
				throw new IdDoesNotExistError();
			}
			List<Record> records = new ArrayList<Record>();
			for (String coverage : coverages) {
				Document document = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder()
						.parse(new InputSource(new StringReader(coverage)));
				String id = document.getElementsByTagName("dc:identifier")
						.item(0).getTextContent();
				Record record = new Record(id, metadataPrefix);

				record.setMetadata(new XSLTMetadataFromRepository(document
						.getDocumentElement()));
				records.add(record);
			}

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until,
			String metadataPrefix) throws CannotDisseminateFormatError,
			NoRecordsMatchError {
		throw new NoRecordsMatchError();
	}

	@Override
	public List<Record> getRecords(String metadataPrefix,
			ResumptionToken resumptionToken)
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

//			String query = "let $xslt-stylesheet := doc('wcs-collection-config/coverage-dc-xslt.xsl') "
//					+ "for $cov in /server[@endpoint='"
//					+ set
//					+ "']/formal/lastimage/harvested/coverages/coverage return xslt:transform($cov, $xslt-stylesheet)";
			String query = createTransformationQuery(set.toString(), null);
			
			List<String> coverages = wcsCatalogManager
					.executeXQueryOnCollectionResources(query);

			if (coverages.size() < 1) {
				throw new IdDoesNotExistError();
			}
			List<Record> records = new ArrayList<Record>();
			for (String coverage : coverages) {
				Document document = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder()
						.parse(new InputSource(new StringReader(coverage)));
				String id = document.getElementsByTagName("dc:identifier")
						.item(0).getTextContent();
				Record record = new Record(id, metadataPrefix);

				record.setMetadata(new XSLTMetadataFromRepository(document
						.getDocumentElement()));
				records.add(record);
			}

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CannotDisseminateFormatError();
		}
	}

	@Override
	public List<Record> getRecords(String metadataPrefix,
			ResumptionToken resumptionToken, SetSpec set)
			throws CannotDisseminateFormatError, NoSetHierarchyError,
			BadResumptionTokenError {
		throw new NoSetHierarchyError();
	}

	@Override
	public List<Record> getRecords(UTCDatetime from, UTCDatetime until,
			String metadataPrefix, ResumptionToken resumptionToken, SetSpec set)
			throws CannotDisseminateFormatError, NoRecordsMatchError,
			NoSetHierarchyError, BadResumptionTokenError {
		throw new NoSetHierarchyError();
	}

	@Override
	public void closeConnection() {
		wcsCatalogManager.releaseManager();
	}

	private static String createTransformationQuery(String endpoint,
			String coverageID) {
		String cID = "";
		if (coverageID != null) {
			cID = "[@id='" + coverageID + "'] ";
		}
		String e = "";
		if (endpoint != null) {
			e = "[@endpoint='" + endpoint + "'] ";
		}

		String xquery = "declare namespace dcn = 'http://purl.org/dc/elements/1.1/'; "
				+ "declare namespace oai_dc='http://www.openarchives.org/OAI/2.0/oai_dc/'; "
				+ "let $xslt-stylesheet := doc('wcs-collection-config/coverage-dc-xslt.xsl') ";

		xquery += "for $server in /server"
				+ e
				+ " "
				+ "for $cov in $server/formal/lastimage/harvested/coverages/coverage"
				+ cID 
				+ " let $creator := $server//*[local-name() = 'ProviderName'] "
				+ " let $abstract := $server//*[local-name() = 'Abstract'] "
				+ " where not(empty($cov)) ";

		xquery += " return copy $tr := xslt:transform($cov, $xslt-stylesheet)"
				+ " modify ( "
				+ " replace value of node $tr/oai_dc:dc/dcn:creator with $creator, "
				+ " replace value of node $tr/oai_dc:dc/dcn:publisher with $creator, "
				+ " replace value of node $tr/oai_dc:dc/dcn:description with $abstract ) return $tr";
		
		return xquery;
	}
}
*/