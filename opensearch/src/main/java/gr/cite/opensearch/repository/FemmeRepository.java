package gr.cite.opensearch.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.interafaces.BackendRepository;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class FemmeRepository implements BackendRepository{

    @Value("${backend.server}")
    private String backendUrl;

    @Value("${backend.search}")
    private String searchPath;

    private static final ObjectMapper mapper = new ObjectMapper();


    public List<FulltextDocument> findBySearchTerm(String searchTerm){
        List<FulltextDocument> fulltextDocuments = new ArrayList<>();
        FulltextSearchQueryMessenger queryMessenger = ConversionUtils.convertSearchtermToFemmeRequest(searchTerm);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(queryMessenger, headers);

        ResponseEntity<FulltextDocument[]> responseEntity = restTemplate.postForEntity(
                backendUrl +searchPath,
                request, FulltextDocument[].class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            fulltextDocuments = Arrays.asList(responseEntity.getBody());
            for (FulltextDocument doc : fulltextDocuments){
                System.out.println("-"+doc.getElementId());
            }
            return fulltextDocuments;
        }

        return fulltextDocuments;
    }
}
