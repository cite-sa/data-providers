package gr.cite.opensearch.utils;

import gr.cite.opensearch.model.elements.MapElements;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class MapAdapter extends XmlAdapter<MapElements[], Map<String, Object>> {
    public MapElements[] marshal(Map<String, Object> arg0) throws Exception {
        MapElements[] mapElements = new MapElements[arg0.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : arg0.entrySet())
            mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

        return mapElements;
    }

    public Map<String, Object> unmarshal(MapElements[] arg0) throws Exception {
        Map<String, Object> r = new HashMap<String, Object>();
        for (MapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
    }
}
