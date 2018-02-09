package gr.cite.opensearch.utils;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class OpenSearchNamespaceMapper extends NamespacePrefixMapper {

    private static final String OPENASEARCH_PREFIX = "opensearch"; // DEFAULT NAMESPACE

    private static final String OPENASEARCH_URI ="http://a9.com/-/spec/opensearch/1.1/";



    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(OPENASEARCH_URI.equals(namespaceUri)) {
            return OPENASEARCH_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { OPENASEARCH_URI };
    }

}
