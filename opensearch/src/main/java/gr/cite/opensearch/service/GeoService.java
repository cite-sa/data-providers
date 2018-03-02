package gr.cite.opensearch.service;

import gr.cite.opensearch.interafaces.GeosServiceApi;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.model.opensearch.OpenSearchResponse;
import gr.cite.opensearch.model.opensearch.Query;
import gr.cite.opensearch.repository.FemmeRepository;
import gr.cite.opensearch.repository.GeoRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoService implements GeosServiceApi{
    private GeoRepository geoRepository;

    @Autowired
    public void setGeoRepository(GeoRepository geoRepository) {
        this.geoRepository = geoRepository;
    }

    @Override
    public OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format) {
        Query query = new Query();
        query.setRole(Query.RoleValue.REQUEST.toString());
        List<CoverageGeo> coverages = geoRepository.findGeoByBbox(bBox,startPage);
        OpenSearchResponse response = null;
        if(format == null){
            response = ConversionUtils.convertGeoResponseToOpenSearchResponseAtom(coverages, query);
        }
        else if(format.equals("atom")) {
            response = ConversionUtils.convertGeoResponseToOpenSearchResponseAtom(coverages, query);
        }
        else if(format.equals("rss")){
            response = ConversionUtils.convertGeoResponseToOpenSearchResponseRSS(coverages, query);
        }
        else{
            response = ConversionUtils.convertGeoResponseToOpenSearchResponseAtom(coverages, query);
        }
        return response;
    }
}
