package gr.cite.opensearch.model.opensearch;

import gr.cite.opensearch.model.elements.Channel;
import gr.cite.opensearch.model.elements.Item;
import gr.cite.opensearch.model.elements.Link;
import gr.cite.opensearch.model.elements.Entry;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rss")
public class OpenSearchResponseRSS extends OpenSearchResponse{
    private String opensearchDescription;

    private String atomDescription;

    private Channel channel;
    @XmlAttribute(namespace = "xmlns", name = "opensearch")
    public String getOpensearchDescription() {
        return opensearchDescription;
    }

    public void setOpensearchDescription(String opensearchDescription) {
        this.opensearchDescription = opensearchDescription;
    }
    @XmlAttribute(namespace = "xmlns", name = "atom")
    public String getAtomDescription() {
        return atomDescription;
    }

    public void setAtomDescription(String atomDescription) {
        this.atomDescription = atomDescription;
    }

    @XmlElement(name = "channel")
    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }


}
