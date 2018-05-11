package gr.cite.opensearch.service;

import gr.cite.femme.core.model.DataElement;
import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.interfaces.FemmeService;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.repository.FemmeRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SearchService implements FemmeService {
	
	private FemmeRepository femmeRepository;
	private ConversionUtils conversionUtils;
	
	@Inject
	public void setFemmeRepository(FemmeRepository femmeRepository, ConversionUtils conversionUtils) {
		this.femmeRepository = femmeRepository;
		this.conversionUtils = conversionUtils;
	}
	
	@Override
	public OpenSearchResponse findBySearchTerm(String searchTerm, String format) throws OpenSearchException {
		Query query = new Query();
		query.setSearchTerms(searchTerm);
		
		List<DataElement> searchResults;
		try {
			searchResults = femmeRepository.findBySearchTerm(searchTerm);
		} catch (Exception e) {
			throw new OpenSearchException(e.getMessage(), e);
		}
		
		if ("atom".equals(format)) {
			return this.conversionUtils.buildAtomResponse(searchResults, query);
		} else if ("rss".equals(format)) {
			return this.conversionUtils.buildRssResponse(searchResults, query);
		} else {
			return this.conversionUtils.buildAtomResponse(searchResults, query);
		}
	}
	
	@Override
	public Query jsonToXmlQuery(String json) {
		return null;
	}
	
	@Override
	public Query findBySearchQuery(Query query) {
		return null;
	}
}
