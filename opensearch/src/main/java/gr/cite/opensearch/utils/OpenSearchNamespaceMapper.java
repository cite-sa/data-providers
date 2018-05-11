/*
package gr.cite.opensearch.utils;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class OpenSearchNamespaceMapper extends NamespacePrefixMapper {
	
	private static final String OPENSEARCH_PREFIX = "opensearch"; // DEFAULT NAMESPACE
	
	private static final String OPENSEARCH_URI = "http://a9.com/-/spec/opensearch/1.1/";
	
	
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if (OPENSEARCH_URI.equals(namespaceUri)) {
			return OPENSEARCH_PREFIX;
		} else if ("http://namespace".equals(namespaceUri) && ! requirePrefix) {
			return "";
		}
		return suggestion;
	}
	
	@Override
	public String[] getPreDeclaredNamespaceUris() {
		return new String[]{OPENSEARCH_URI};
	}
	
}
*/
