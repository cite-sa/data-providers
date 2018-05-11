package gr.cite.opensearch.model;

import gr.cite.opensearch.model.rss.Channel;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
