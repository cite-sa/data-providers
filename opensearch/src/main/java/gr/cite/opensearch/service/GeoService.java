package gr.cite.opensearch.service;

import gr.cite.femme.client.FemmeClient;
import gr.cite.femme.client.FemmeException;
import gr.cite.femme.client.api.FemmeClientAPI;
import gr.cite.femme.core.model.DataElement;
import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.interfaces.GeosServiceApi;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.repository.FemmeRepository;
import gr.cite.opensearch.repository.GeoRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoService implements GeosServiceApi {
	private static final Logger logger = LoggerFactory.getLogger(GeosServiceApi.class);
	
	private FemmeRepository femmeRepository;
	private GeoRepository geoRepository;
	private ConversionUtils conversionUtils;
	
	@Inject
	public GeoService(FemmeRepository femmeRepository, GeoRepository geoRepository, ConversionUtils conversionUtils) {
		this.femmeRepository = femmeRepository;
		this.geoRepository = geoRepository;
		this.conversionUtils = conversionUtils;
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
		
		if ("atom".equals(format)) {
			return this.conversionUtils.buildAtomResponse(dataElements, query);
		} else if ("rss".equals(format)) {
			return this.conversionUtils.buildRssResponse(dataElements, query);
		} else {
			return this.conversionUtils.buildAtomResponse(dataElements, query);
		}
	}
}
