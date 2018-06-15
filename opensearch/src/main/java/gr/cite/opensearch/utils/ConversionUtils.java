package gr.cite.opensearch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cite.femme.core.model.DataElement;
import gr.cite.femme.core.model.Metadatum;
import gr.cite.opensearch.model.atom.Content;
import gr.cite.opensearch.model.atom.Entry;
import gr.cite.opensearch.model.femme.FulltextDocument;
import gr.cite.opensearch.model.femme.FulltextField;
import gr.cite.opensearch.model.femme.FulltextSearchQueryMessenger;
import gr.cite.opensearch.model.geo.CoverageGeo;
import gr.cite.opensearch.model.OpenSearchResponse;
import gr.cite.opensearch.model.atom.OpenSearchResponseAtom;
import gr.cite.opensearch.model.OpenSearchResponseRSS;
import gr.cite.opensearch.model.Query;
import gr.cite.opensearch.model.rss.Channel;
import gr.cite.opensearch.model.rss.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ConversionUtils {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final MapAdapter mapAdapter = new MapAdapter();
	
	private String femmeServiceUrl;
	private String dataElementsPath;
	private String metadataPath;
	
	@Inject
	public ConversionUtils(@Value("${femme.datastore.url.external}") String femmeServiceUrl,
						   @Value("${femme.datastore.dataElements.path}") String dataElementsPath,
						   @Value("${femme.datastore.metadata.path}") String metadataPath) {
		this.femmeServiceUrl = femmeServiceUrl;
		this.dataElementsPath = dataElementsPath;
		this.metadataPath = metadataPath;
	}
	
	public static FulltextSearchQueryMessenger convertSearchTermToFemmeRequest(String searchTerm) {
		FulltextSearchQueryMessenger queryMessenger = new FulltextSearchQueryMessenger();
		queryMessenger.setAutocompleteField(new FulltextField("name", searchTerm));
		
		return queryMessenger;
	}
	
	public Query convertFemmeResponseToQuery(List<FulltextDocument> fulltextDocuments, String searchTerm) {
		Query query = new Query();
		for (FulltextDocument doc : fulltextDocuments) {
			System.out.println("-" + doc.getElementId());
		}
		
		query.setSearchTerms(searchTerm);
		query.setTotalResults(BigInteger.valueOf(fulltextDocuments.size()));
		
		return query;
	}
	
	public OpenSearchResponseAtom buildAtomResponse(List<DataElement> dataElements, Query query) {
		OpenSearchResponseAtom response = new OpenSearchResponseAtom();
		
		response.setQuery(query);
		response.setEntries(dataElements.stream().map(this::buildDataElementMetadataEntries).flatMap(entry -> entry).collect(Collectors.toList()));
		response.setCount(dataElements.size());
		response.setTotalResults(response.getEntries().size());
		
		return response;
	}
	
	private Stream<Entry> buildDataElementMetadataEntries(DataElement dataElement) {
		return dataElement.getMetadata().stream().map(metadatum -> buildDataElementMetadatumEntry(dataElement.getId(), dataElement.getName(), metadatum));
	}
	
	private Entry buildDataElementMetadatumEntry(String dataElementId, String dataElementName, Metadatum metadatum) {
		Entry entry = new Entry();
		
		entry.setId(this.femmeServiceUrl + "/" + this.dataElementsPath + "/" + dataElementId + "/" + this.metadataPath + "/" + metadatum.getId());
		entry.setTitle(dataElementName + "_" + metadatum.getName());
		entry.setUpdated(metadatum.getSystemicMetadata().getModified().toString());
		
		entry.setContent(buildMetadatumContent(metadatum));
		
		return entry;
	}
	
	private Content buildMetadatumContent(Metadatum metadatum) {
		Content content = new Content();
		
		content.setType(metadatum.getContentType());
		content.setValue(metadatum.getValue());
		
		return content;
	}
	
	public OpenSearchResponseRSS buildRssResponse(List<DataElement> dataElements, Query query) {
		OpenSearchResponseRSS response = new OpenSearchResponseRSS();
		
		response.setQuery(query);
		response.setChannel(buildChannel(dataElements));
		response.setTotalResults(response.getChannel().getItems().size());
		return response;
	}
	
	private Channel buildChannel(List<DataElement> dataElements) {
		Channel channel = new Channel();
		channel.setItems(dataElements.stream().map(this::buildDataElementMetadataItems).flatMap(item -> item).collect(Collectors.toList()));
		return channel;
	}
	
	private Stream<Item> buildDataElementMetadataItems(DataElement dataElement) {
		return dataElement.getMetadata().stream().map(metadatum -> buildDataElementMetadatumItem(dataElement.getId(), dataElement.getName(), metadatum));
	}
	
	private Item buildDataElementMetadatumItem(String dataElementId, String dataElementName, Metadatum metadatum) {
		Item item = new Item();
		
		item.setGuid(this.femmeServiceUrl + "/" + this.dataElementsPath + "/" + dataElementId + "/" + this.metadataPath + "/" + metadatum.getId());
		item.setLink(this.femmeServiceUrl + "/" + this.dataElementsPath + "/" + dataElementId + "/" + this.metadataPath + "/" + metadatum.getId());
		item.setTitle(dataElementName + "_" + metadatum.getName());
		item.setPubDate(metadatum.getSystemicMetadata().getModified().toString());
		
		return item;
	}
	
	
	public static OpenSearchResponse convertGeoResponseToOpenSearchResponseAtom(List<CoverageGeo> coverages, Query query) {
		OpenSearchResponseAtom response = new OpenSearchResponseAtom();
		
		/*response.setTotalResults(coverages.size());
		response.setQuery(query);
		ArrayList<Entry> entries = new ArrayList<>();
		
		for (CoverageGeo geo : coverages) {
			Entry entry = new Entry();
			Content content = new Content();
			Content dataContent = new Content();
			Content metadataContent = new Content();
			dataContent.setType("element");
			MapElements elementMap = new MapElements(geo.getId(), "http://femme/dataElements/" + geo.getId());
			dataContent.setEntry(elementMap);
			content.setType("geo");
			content.setId(geo.getId());
			
			List<Content> contents = asList(content, dataContent, metadataContent);
			String boxLine = "";
			
			for (List<LngLatAlt> box : ((Polygon) geo.getGeo()).getCoordinates()) {
				for (LngLatAlt point : box) {
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
		
		response.setEntries(entries);*/
		return response;
	}
	
	public static OpenSearchResponse convertGeoResponseToOpenSearchResponseRSS(List<CoverageGeo> coverages, Query query) {
		OpenSearchResponseRSS response = new OpenSearchResponseRSS();
		/*Channel channel = new Channel();
		channel.setTotalResults(coverages.size());
		channel.setQuery(query);
		ArrayList<Item> items = new ArrayList<>();
		for (CoverageGeo geo : coverages) {
			Item item = new Item();
			Content content = new Content();
			content.setType("fulltext");
			content.setId(geo.getId());
			
			
			Content dataContent = new Content();
			Content metadataContent = new Content();
			dataContent.setType("element");
			MapElements elementMap = new MapElements(geo.getId(), "http://femme/dataElements/" + geo.getId());
			dataContent.setEntry(elementMap);
			
			
			List<Content> contents = asList(content, dataContent);
			String boxLine = "";
			for (List<LngLatAlt> box : ((Polygon) geo.getGeo()).getCoordinates()) {
				for (LngLatAlt point : box) {
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
		channel.setItems(items);
		response.setChannel(channel);*/
		return response;
	}
}
