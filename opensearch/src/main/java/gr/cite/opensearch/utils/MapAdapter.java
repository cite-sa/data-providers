package gr.cite.opensearch.utils;

import gr.cite.opensearch.model.MapElements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapElements[], Map<String, Object>> {
    public MapElements[] marshal(Map<String, Object> arg0) {
        MapElements[] mapElements = new MapElements[arg0.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

        return mapElements;
    }

    public Map<String, Object> unmarshal(MapElements[] mapElements) {
        Map<String, Object> r = new HashMap<>();
        for (MapElements mapElement : mapElements)
            r.put(mapElement.key, mapElement.value);
        return r;
    }
}
