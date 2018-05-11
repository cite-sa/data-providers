package gr.cite.opensearch.interafaces;

import gr.cite.opensearch.model.opensearch.Query;
import gr.cite.opensearch.model.opensearch.OpenSearchResponse;

public interface FemmeService {

    OpenSearchResponse findBySearchTerm(String searchTerm, String format);

    Query jsonToXmlQuery(String json);

    Query findBySearchQuery(Query query);
}
