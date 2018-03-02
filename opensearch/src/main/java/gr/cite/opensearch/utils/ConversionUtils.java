package gr.cite.opensearch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.opensearch.model.elements.*;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextField;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.model.opensearch.OpenSearchResponse;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseAtom;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseRSS;
import gr.cite.opensearch.model.opensearch.Query;
import org.geojson.GeoJsonObject;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ConversionUtils {
    private static MapAdapter mapAdapter = new MapAdapter();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static FulltextSearchQueryMessenger convertSearchtermToFemmeRequest(String searchTerm){
        FulltextSearchQueryMessenger queryMessenger = new FulltextSearchQueryMessenger();
        queryMessenger.setAutocompleteField(new FulltextField("all",searchTerm));

        return queryMessenger;
    }

    public static Query convertFemmeResponseToQuery(List<FulltextDocument> fulltextDocuments,String searchTerm){
        Query query = new Query();
        for (FulltextDocument doc : fulltextDocuments){
            System.out.println("-"+doc.getElementId());
        }

        query.setSearchTerms(searchTerm);
        query.setTotalResults(BigInteger.valueOf(fulltextDocuments.size()));
       // query.set

        return query;
    }

    public static OpenSearchResponseAtom convertFemmeResponseToOpenSearchResponseAtom(List<FulltextDocument> fulltextDocuments, Query query) {
        OpenSearchResponseAtom response = new OpenSearchResponseAtom();

        response.setTotalResults(fulltextDocuments.size());
        response.setQuery(query);
        ArrayList<Entry> entries = new ArrayList<>();
        for (FulltextDocument doc : fulltextDocuments){
            Entry entry = new Entry();
            Content content = new Content();
            Content dataContent = new Content();
            Content metadataContent = new Content();
            dataContent.setType("element");
            MapElements elementMap = new MapElements(doc.getElementId(), "http://femme/dataElements/"+doc.getElementId());
            dataContent.setEntry(elementMap);
            metadataContent.setType("metadata");
            MapElements metadataMap = new MapElements(doc.getMetadatumId(),"http://femme/dataElements/id/metadata/"+doc.getMetadatumId());
            metadataContent.setEntry(metadataMap);

            content.setType("fulltext");
            content.setId(doc.getElementId());
//            content.setElementId(doc.getElementId());
            content.setMetadatumId(doc.getMetadatumId());
            content.setMapProperty(doc.getFulltextFields());

            List<Content> contents = asList(content,dataContent,metadataContent);

            entry.setContent(contents);
            entries.add(entry);

        }
        response.setEntry(entries);
        return response;
    }
    public static OpenSearchResponseRSS convertFemmeResponseToOpenSearchResponseRSS(List<FulltextDocument> fulltextDocuments, Query query) {
        OpenSearchResponseRSS response = new OpenSearchResponseRSS();
        Channel channel= new Channel();
        channel.setTotalResults(fulltextDocuments.size());
        channel.setQuery(query);
        ArrayList<Item> items = new ArrayList<>();
        for (FulltextDocument doc : fulltextDocuments){
            Item item = new Item();
            Content content = new Content();
            content.setType("fulltext");
            content.setId(doc.getElementId());
//            content.setElementId(doc.getElementId());
            content.setMetadatumId(doc.getMetadatumId());
            content.setMapProperty(doc.getFulltextFields());

            Content dataContent = new Content();
            Content metadataContent = new Content();
            dataContent.setType("element");
            MapElements elementMap = new MapElements(doc.getElementId(), "http://femme/dataElements/"+doc.getElementId());
            dataContent.setEntry(elementMap);
            metadataContent.setType("metadata");
            MapElements metadataMap = new MapElements(doc.getMetadatumId(),"http://femme/dataElements/id/metadata/"+doc.getMetadatumId());
            metadataContent.setEntry(metadataMap);

            List<Content> contents = asList(content,dataContent,metadataContent);


            item.setContent(contents);
            items.add(item);
        }
        channel.setItem(items);
        response.setChannel(channel);
        return response;
    }

    public static OpenSearchResponse convertGeoResponseToOpenSearchResponseAtom(List<CoverageGeo> coverages, Query query) {
        OpenSearchResponseAtom response = new OpenSearchResponseAtom();

        response.setTotalResults(coverages.size());
        response.setQuery(query);
        ArrayList<Entry> entries = new ArrayList<>();

        for (CoverageGeo geo : coverages) {
            Entry entry = new Entry();
            Content content = new Content();
            Content dataContent = new Content();
            Content metadataContent = new Content();
            dataContent.setType("element");
            MapElements elementMap = new MapElements(geo.getId(), "http://femme/dataElements/"+geo.getId());
            dataContent.setEntry(elementMap);
            content.setType("geo");
            content.setId(geo.getId());

            List<Content> contents = asList(content,dataContent,metadataContent);
            String boxLine="";

            for(List<LngLatAlt> box:  ((Polygon) geo.getGeo()).getCoordinates()){
                for(LngLatAlt point:  box) {
                    if (boxLine.equals("")) {
                        boxLine = String.valueOf(point.getLatitude()) + " " + String.valueOf(point.getLongitude());
                    } else {
                        boxLine += " " + String.valueOf(point.getLatitude()) + " " + String.valueOf(point.getLongitude());
                    }
                }
            }
            entry.setBox(boxLine);
            entry.setContent(contents);
            entries.add(entry);

        }

        response.setEntry(entries);
        return response;
    }

    public static OpenSearchResponse convertGeoResponseToOpenSearchResponseRSS(List<CoverageGeo> coverages,Query query) {
        OpenSearchResponseRSS response = new OpenSearchResponseRSS();
        Channel channel= new Channel();
        channel.setTotalResults(coverages.size());
        channel.setQuery(query);
        ArrayList<Item> items = new ArrayList<>();
        for (CoverageGeo geo : coverages){
            Item item = new Item();
            Content content = new Content();
            content.setType("fulltext");
            content.setId(geo.getId());


            Content dataContent = new Content();
            Content metadataContent = new Content();
            dataContent.setType("element");
            MapElements elementMap = new MapElements(geo.getId(), "http://femme/dataElements/"+geo.getId());
            dataContent.setEntry(elementMap);


            List<Content> contents = asList(content,dataContent);
            String boxLine="";
            for(List<LngLatAlt> box:  ((Polygon) geo.getGeo()).getCoordinates()){
                for(LngLatAlt point:  box) {
                    if (boxLine.equals("")) {
                        boxLine = String.valueOf(point.getLatitude()) + " " + String.valueOf(point.getLongitude());
                    } else {
                        boxLine += " " + String.valueOf(point.getLatitude()) + " " + String.valueOf(point.getLongitude());
                    }
                }
            }
            item.setBox(boxLine);
            item.setContent(contents);
            items.add(item);
        }
        channel.setItem(items);
        response.setChannel(channel);
        return response;
    }
}
