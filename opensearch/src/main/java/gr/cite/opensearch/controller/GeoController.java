package gr.cite.opensearch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.model.Format;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.atom.OpenSearchResponseAtom;
import gr.cite.opensearch.model.OpenSearchResponseRSS;
import gr.cite.opensearch.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

//import gr.cite.opensearch.utils.OpenSearchNamespaceMapper;

@Controller
public class GeoController {
	
	private GeoService geoService;
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(GeoController.class);
	
	
	@Autowired
	public void setSearchService(GeoService geoService) {
		this.geoService = geoService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params = {"lat", "lon"})
	public @ResponseBody
	String geoQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value = "pw", required = false) String startPage,
					@RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lon", required = false) String lon,
					@RequestParam(value = "radius", required = false) String rad, @RequestParam(value = "format", required = false) String format) {
		
		String xmlString = "";
		Double latitude = null, longitude = null, radius = 0.0;
		if (lat != null && lon != null) {
			try {
				latitude = Double.parseDouble(lat);
				longitude = Double.parseDouble(lat);
				if (rad != null) {
					radius = Double.parseDouble(rad);
				}
			} catch (NumberFormatException e) {
				logger.error("wrong input");
				xmlString = "wrong input";
				return xmlString;
			}
		}
		
		
		// searchService.findGeoByPoint(latitude,longitude,radius);
		
		return "point";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params = {"bbox"})
	public @ResponseBody
	String geoQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value = "pw", required = false) String startPage,
					@RequestParam(value = "bbox", required = false) String bbox, @RequestParam(value = "format", required = false) String format) {
		String xmlString = "";
		
		OpenSearchResponse response = geoService.findGeoByBbox(bbox, startPage, format);
		try {
			JAXBContext ctx = null;
			if (format == null) {
				ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
			} else if (format.equals(Format.RSS.getFormat())) {
				ctx = JAXBContext.newInstance(OpenSearchResponseRSS.class);
			} else if (format.equals(Format.ATOM.getFormat())) {
				ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
			}
			Marshaller m = ctx.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new OpenSearchNamespaceMapper());
			StringWriter sw = new StringWriter();
			
			m.marshal(response, sw);
			xmlString = sw.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return xmlString;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params = {"g"})
	public @ResponseBody
	String geometriesQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value = "pw", required = false) String startPage,
						   @RequestParam(value = "g", required = false) String geometries, @RequestParam(value = "format", required = false) String format) {
		System.out.println("geoCalled");
		return "geo";
	}
}
