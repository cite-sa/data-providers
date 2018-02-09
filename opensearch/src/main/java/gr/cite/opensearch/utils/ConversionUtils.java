package gr.cite.opensearch.utils;

import gr.cite.opensearch.model.elements.Channel;
import gr.cite.opensearch.model.elements.Content;
import gr.cite.opensearch.model.elements.Entry;
import gr.cite.opensearch.model.elements.Item;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextField;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseAtom;
import gr.cite.opensearch.model.opensearch.OpenSearchResponseRSS;
import gr.cite.opensearch.model.opensearch.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
            content.setType("fulltext");
            content.setId(doc.getId());
            content.setElementId(doc.getElementId());
            content.setMetadatumId(doc.getMetadatumId());
            content.setMapProperty(doc.getFulltextFields());
            entry.setContent(content);
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
            content.setId(doc.getId());
            content.setElementId(doc.getElementId());
            content.setMetadatumId(doc.getMetadatumId());
            content.setMapProperty(doc.getFulltextFields());
            item.setContent(content);
            items.add(item);
        }
        channel.setItem(items);
        response.setChannel(channel);
        return response;
    }

}
