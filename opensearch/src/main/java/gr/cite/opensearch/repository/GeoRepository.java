package gr.cite.opensearch.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class GeoRepository {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private String geoUrl;
	private String geoBBoxPath;
	
	@Inject
	public GeoRepository(@Value("${femme.geo.url}") String geoUrl, @Value("${femme.geo.bbox.path}") String geoBBoxPath) {
		this.geoUrl = geoUrl;
		this.geoBBoxPath = geoBBoxPath;
	}
	
	public List<CoverageGeo> findGeoByBbox(String bBox, String startPage) {
		List<CoverageGeo> coverages = new ArrayList<>();
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// HttpEntity<Object> request = new HttpEntity<>(queryMessenger, headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geoUrl + "/" + geoBBoxPath)
										   .queryParam("bbox", bBox);
//        HttpEntity<Object> request = new HttpEntity<>(builder, headers);

//        ResponseEntity<CoverageGeo[]> responseEntity = restTemplate.getForEntity(
//                backendUrl +searchPath,
//                request, CoverageGeo[].class);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		ResponseEntity<CoverageGeo[]> responseEntity = restTemplate.exchange(
			builder.build().encode().toUri(),
			HttpMethod.GET,
			entity,
			CoverageGeo[].class);
		
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			coverages = Arrays.asList(responseEntity.getBody());
			for (CoverageGeo geo : coverages) {
				System.out.println("-" + geo.getId());
			}
			return coverages;
		}
		
		return coverages;
	}
}
