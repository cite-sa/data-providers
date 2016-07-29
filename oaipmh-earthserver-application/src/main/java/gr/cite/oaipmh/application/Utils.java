package gr.cite.oaipmh.application;

public class Utils {
	private static String XSLT_FILENAME = "coverage.xsl";

	public static String getXSLTfilename() {
		return Thread.currentThread().getContextClassLoader()
				.getResource(XSLT_FILENAME).getPath();
	}

}
