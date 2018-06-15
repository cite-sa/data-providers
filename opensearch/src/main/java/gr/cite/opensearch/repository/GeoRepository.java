package gr.cite.opensearch.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.model.geo.CoverageGeo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GeoRepository {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private WebTarget geoService;
	private String geoBBoxPath;
	
	@Inject
	public GeoRepository(@Value("${femme.geo.url}") String geoUrl, @Value("${femme.geo.bbox.path}") String geoBBoxPath) {
		this.geoBBoxPath = geoBBoxPath;
		this.geoService = ClientBuilder.newClient().target(geoUrl);
	}
	
	public List<CoverageGeo> findGeoByBbox(String bBox, String startPage) throws OpenSearchException {
		Response response = this.geoService.path(this.geoBBoxPath).queryParam("bbox", bBox).request().get();
		
		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			throw new OpenSearchException("Error on querying geo service: " + response.readEntity(String.class));
		}
		
		return response.readEntity(new GenericType<List<CoverageGeo>>(){});
	}
}
