package gr.cite.opensearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.OpenSearchException;
import gr.cite.opensearch.model.Format;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.atom.OpenSearchResponseAtom;
import gr.cite.opensearch.model.OpenSearchResponseRSS;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Controller
@ControllerAdvice
public class SearchController {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private SearchService searchService;
	
	@Inject
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public String findQuery(
					@RequestParam(value = "q", required = false) String searchTerm,
					@RequestParam(value = "bbox", required = false) String bbox,
					@RequestParam(value = "pw", required = false) String startPage,
					@RequestParam(value = "format", required = false, defaultValue = "atom") String format) throws OpenSearchException {
		
		OpenSearchResponse response;
		if (searchTerm != null && ! "".equals(searchTerm) && bbox != null && ! "".equals(bbox)) {
			response = this.searchService.findBySearchTermAndGeoBBox(searchTerm, bbox, startPage, format);
		} else if (searchTerm != null && ! "".equals(searchTerm)) {
			response = searchService.findBySearchTerm(searchTerm, format);
		} else if (bbox != null && ! "".equals(bbox)) {
			response = this.searchService.findGeoByBbox(bbox, startPage, format);
		} else {
			throw new IllegalArgumentException("Either \"q\" or \"bbox\" must be defined");
		}
		
		try {
			JAXBContext ctx;
			if (Format.ATOM.getFormat().equals(format)) {
				ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
			} else if (Format.RSS.getFormat().equals(format)) {
				ctx = JAXBContext.newInstance(OpenSearchResponseRSS.class);
			} else {
				ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
			}
			
			Marshaller m = ctx.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new OpenSearchNamespaceMapper());
			
			StringWriter sw = new StringWriter();
			m.marshal(response, sw);
			return sw.toString();
			
		} catch (JAXBException e) {
			throw new OpenSearchException("Error on marshalling response", e);
		}
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody
	OpenSearchResponse findByUrlQuery(@RequestBody Query queryRequest) {
		System.out.println("ive been called with request" + queryRequest.toString());
		return null;
	}
	
	/*@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/xml")
	public @ResponseBody
	Query search(@RequestBody Query query) {
		System.out.println("ive been called with request" + query.toString());
		
		return searchService.findBySearchQuery(query);
	}*/
}
