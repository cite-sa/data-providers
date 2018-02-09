package gr.cite.opensearch.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.model.elements.Format;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseAtom;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseRSS;
import gr.cite.opensearch.model.opensearch.Query;
import gr.cite.opensearch.model.opensearch.OpenSearchResponse;
import gr.cite.opensearch.service.SearchService;
import gr.cite.opensearch.utils.OpenSearchNamespaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Controller
public class BasicController {

    private SearchService SearchService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setSearchService(SearchService SearchService) {
        this.SearchService = SearchService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/xml", params = "q")
    public @ResponseBody
    String findQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value = "format", required = false) String format){
        String xmlString ="";
        OpenSearchResponse response = SearchService.findBySearchTerm(searchTerm, format);
        try {
            JAXBContext ctx = null;
            if(format == null){
                ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
            }
            else if( format.equals(Format.RSS.getFormat())){
                ctx = JAXBContext.newInstance(OpenSearchResponseRSS.class);
            }
            else if( format.equals(Format.ATOM.getFormat())){
                ctx = JAXBContext.newInstance(OpenSearchResponseAtom.class);
            }
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new OpenSearchNamespaceMapper());
            StringWriter sw = new StringWriter();

            m.marshal(response, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlString;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params={"lat","long"})
    public @ResponseBody
    String geoQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value="pw", required = false) String startPage,
                    @RequestParam(value = "lat", required = false) String latitude, @RequestParam(value = "lon", required = false) String longitude,
                    @RequestParam(value = "radius", required = false) String radius,@RequestParam(value = "format", required = false)String format){


        return "geo";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params={"bbox"})
    public @ResponseBody
    String geoQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value="pw", required = false) String startPage,
                    @RequestParam(value = "bbox", required = false) String bbox,@RequestParam(value = "format", required = false)String format){
        System.out.println("geoCalled");
        return "geo";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/xml", params={"g"})
    public @ResponseBody
    String geometriesQuery(@RequestParam(value = "q", required = false) String searchTerm, @RequestParam(value="pw", required = false) String startPage,
                    @RequestParam(value = "g", required = false) String geometries,@RequestParam(value = "format", required = false)String format){
        System.out.println("geoCalled");
        return "geo";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody OpenSearchResponse findByUrlQuery(@RequestBody Query queryRequest){
        System.out.println("ive been called with request" + queryRequest.toString());
        return null;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/xml")
    public @ResponseBody Query search(@RequestBody Query query){
        System.out.println("ive been called with request" + query.toString());

        return SearchService.findBySearchQuery(query);
    }
}
