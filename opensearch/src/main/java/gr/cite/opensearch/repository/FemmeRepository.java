package gr.cite.opensearch.repository;

import gr.cite.femme.client.FemmeClient;
import gr.cite.femme.client.FemmeException;
import gr.cite.femme.core.model.DataElement;
import gr.cite.opensearch.interfaces.BackendRepository;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.model.femme.FulltextSemanticResult;
import gr.cite.opensearch.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class FemmeRepository implements BackendRepository {
	private static final Logger logger = LoggerFactory.getLogger(FemmeRepository.class);
	
	private String searchPath;
	private WebTarget searchClient;
	private FemmeClient femmeClient;
	
	@Inject
	public FemmeRepository(@Value("${femme.search.url}") String searchUrl, @Value("${femme.search.elements.path}") String searchPath,
						   @Value("${femme.datastore.url}") String datastoreUrl) {
		this.searchPath = searchPath;
		this.searchClient = ClientBuilder.newClient().target(searchUrl);
		this.femmeClient = new FemmeClient(datastoreUrl);
	}
	
	public List<DataElement> findBySearchTerm(String searchTerm) {
		FulltextSearchQueryMessenger queryMessenger = ConversionUtils.convertSearchTermToFemmeRequest(searchTerm);
		
		List<FulltextDocument> results = this.searchClient.path(this.searchPath).request(MediaType.APPLICATION_JSON)
								.post(Entity.entity(queryMessenger, MediaType.APPLICATION_JSON), new GenericType<List<FulltextSemanticResult>>() {})
			.stream().map(FulltextSemanticResult::getFulltextResult).collect(Collectors.toList());
		
		return results.stream().map(result -> {
			try {
				return this.femmeClient.getDataElementById(result.getElementId());
			} catch (FemmeException e) {
				logger.error(e.getMessage());
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.toList());
		
		
		
		
	}
}
