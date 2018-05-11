package gr.cite.opensearch.service;

import gr.cite.opensearch.interfaces.GeosServiceApi;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.repository.GeoRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoService implements GeosServiceApi {
	private GeoRepository geoRepository;
	private ConversionUtils conversionUtils;
	
	@Autowired
	public void setGeoRepository(GeoRepository geoRepository, ConversionUtils conversionUtils) {
		this.geoRepository = geoRepository;
		this.conversionUtils = conversionUtils;
	}
	
	@Override
	public OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format) {
		Query query = new Query();
		
		query.setRole(Query.RoleValue.REQUEST.toString());
		List<CoverageGeo> coverages = geoRepository.findGeoByBbox(bBox, startPage);
		OpenSearchResponse response;
		
		if ("atom".equals(format)) {
			response = ConversionUtils.convertGeoResponseToOpenSearchResponseAtom(coverages, query);
		} else if ("rss".equals(format)) {
			response = ConversionUtils.convertGeoResponseToOpenSearchResponseRSS(coverages, query);
		} else {
			response = ConversionUtils.convertGeoResponseToOpenSearchResponseAtom(coverages, query);
		}
		
		return response;
	}
}
