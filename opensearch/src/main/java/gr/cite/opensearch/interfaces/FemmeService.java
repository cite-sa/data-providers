package gr.cite.opensearch.interfaces;

import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.model.OpenSearchResponse;

public interface FemmeService {
    OpenSearchResponse findBySearchTerm(String searchTerm, String format) throws OpenSearchException;
	OpenSearchResponse findGeoByBbox(String bBox, String startPage, String format) throws OpenSearchException;
	OpenSearchResponse findBySearchTermAndGeoBBox(String searchTerm, String bBox, String startPage, String format) throws OpenSearchException;
}
