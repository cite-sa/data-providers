package gr.cite.opensearch.service;

import gr.cite.opensearch.interafaces.FemmeService;
import gr.cite.opensearch.model.opensearch.OpenSearchResponse;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseAtom;
import gr.cite.opensearch.model.opensearch.Query;
import gr.cite.opensearch.repository.FemmeRepository;
import gr.cite.opensearch.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements FemmeService{

    private FemmeRepository femmeRepository;

    @Autowired
    public void setFemmeRepository(FemmeRepository femmeRepository) {
        this.femmeRepository = femmeRepository;
    }

    @Override
    public OpenSearchResponse findBySearchTerm(String searchTerm, String format){
        Query query= new Query();
        query.setSearchTerms(searchTerm);
        OpenSearchResponse response = null;
        if(format == null){
            response = ConversionUtils.convertFemmeResponseToOpenSearchResponseAtom(femmeRepository.findBySearchTerm(searchTerm), query);
        }
        else if(format.equals("atom")) {
            response = ConversionUtils.convertFemmeResponseToOpenSearchResponseAtom(femmeRepository.findBySearchTerm(searchTerm), query);
        }
        else if(format.equals("rss")){
            response = ConversionUtils.convertFemmeResponseToOpenSearchResponseRSS(femmeRepository.findBySearchTerm(searchTerm), query);
        }
        else{
            response = ConversionUtils.convertFemmeResponseToOpenSearchResponseAtom(femmeRepository.findBySearchTerm(searchTerm), query);
        }
        return response ;
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
