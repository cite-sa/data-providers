package gr.cite.opensearch.interfaces;

import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.model.OpenSearchResponse;

public interface GeosServiceApi {
    public OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format) throws OpenSearchException;
}
