package gr.cite.opensearch.interfaces;

import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.model.OpenSearchResponse;

public interface FemmeService {
    OpenSearchResponse findBySearchTerm(String searchTerm, String format) throws OpenSearchException;
    Query jsonToXmlQuery(String json);
    Query findBySearchQuery(Query query);
}
