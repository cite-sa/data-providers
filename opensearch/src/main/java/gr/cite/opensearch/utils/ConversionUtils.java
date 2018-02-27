package gr.cite.opensearch.utils;

import gr.cite.opensearch.model.elements.*;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextField;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseAtom;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseRSS;
import gr.cite.opensearch.model.opensearch.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class ConversionUtils {
    private static MapAdapter mapAdapter = new MapAdapter();

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

}
