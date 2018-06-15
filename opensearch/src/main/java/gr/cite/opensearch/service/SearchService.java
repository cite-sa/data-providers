package gr.cite.opensearch.service;

import gr.cite.femme.client.FemmeException;
import gr.cite.femme.core.model.DataElement;
import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.interfaces.FemmeService;
import gr.cite.opensearch.model.Format;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.repository.FemmeRepository;
import gr.cite.opensearch.repository.GeoRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService implements FemmeService {
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
	
	private FemmeRepository femmeRepository;
	private GeoRepository geoRepository;
	private ConversionUtils conversionUtils;
	
	@Inject
	public SearchService(FemmeRepository femmeRepository, GeoRepository geoRepository, ConversionUtils conversionUtils) {
		this.femmeRepository = femmeRepository;
		this.geoRepository = geoRepository;
		this.conversionUtils = conversionUtils;
	}
	
	@Override
	public OpenSearchResponse findBySearchTerm(String searchTerm, String format) throws OpenSearchException {
		Query query = new Query();
		query.setSearchTerms(searchTerm);
		
		List<FulltextDocument> searchResults;
		try {
			searchResults = this.femmeRepository.findBySearchTerm(searchTerm);
		} catch (Exception e) {
			throw new OpenSearchException(e.getMessage(), e);
		}
		
		List<DataElement> dataElements = new ArrayList<>();
		for (FulltextDocument searchResult: searchResults) {
			try {
				DataElement dataElement = this.femmeRepository.getDataElementById(searchResult.getElementId());
				dataElements.add(dataElement);
			} catch (FemmeException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return buildOpenSearchResponse(dataElements, query, format);
	}
	
	@Override
	public OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format) throws OpenSearchException {
		Query query = new Query();
		
		query.setRole(Query.RoleValue.REQUEST.toString());
		
		List<CoverageGeo> coverages = this.geoRepository.findGeoByBbox(bBox, startPage);
		
		List<DataElement> dataElements = new ArrayList<>();
		for (CoverageGeo coverage: coverages) {
			try {
				DataElement dataElement = this.femmeRepository.getDataElementById(coverage.getDataElementId());
				dataElements.add(dataElement);
			} catch (FemmeException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return buildOpenSearchResponse(dataElements, query, format);
	}
	
	@Override
	public OpenSearchResponse findBySearchTermAndGeoBBox(String searchTerm, String bBox, String startPage, String format) throws OpenSearchException {
		Query query = new Query();
		query.setSearchTerms(searchTerm);
		
		List<FulltextDocument> documents = this.femmeRepository.findBySearchTerm(searchTerm);
		List<CoverageGeo> coverages = this.geoRepository.findGeoByBbox(bBox, startPage);
		
		Set<String> dataElementIds = new HashSet<>();
		documents.forEach(document -> dataElementIds.add(document.getElementId()));
		coverages.forEach(coverage-> dataElementIds.add(coverage.getDataElementId()));
		
		List<DataElement> dataElements = new ArrayList<>();
		for (String dataElementId: dataElementIds) {
			try {
				DataElement dataElement = this.femmeRepository.getDataElementById(dataElementId);
				dataElements.add(dataElement);
			} catch (FemmeException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return buildOpenSearchResponse(dataElements, query, format);
	}
	
	private OpenSearchResponse buildOpenSearchResponse(List<DataElement> dataElements, Query query, String format) {
		if (Format.ATOM.getFormat().equals(format)) {
			return this.conversionUtils.buildAtomResponse(dataElements, query);
		} else if (Format.RSS.getFormat().equals(format)) {
			return this.conversionUtils.buildRssResponse(dataElements, query);
		} else {
			return this.conversionUtils.buildAtomResponse(dataElements, query);
		}
	}

}
