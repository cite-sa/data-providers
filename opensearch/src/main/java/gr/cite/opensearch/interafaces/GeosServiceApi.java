package gr.cite.opensearch.interafaces;

import gr.cite.opensearch.model.opensearch.OpenSearchResponse;

public interface GeosServiceApi {
    public OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format);
}
