<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="coverage">
		<oai_dc:dc xmlns:oai_dc="http://www.openarchives.org/OAI/2.0/oai_dc/"
			xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd">
			<dc:title><xsl:value-of select="@id" /></dc:title>
			<dc:creator></dc:creator>
			<dc:subject></dc:subject>
			<dc:description>
			it gives namespace error during transformation
				<xsl:copy-of select="//*[local-name()='coveragedescription']" />
			</dc:description>
			<dc:publisher><xsl:value-of select="/server/@endpoint" /></dc:publisher> not working because /server is out of scope
			<dc:contributor></dc:contributor>
			<dc:date></dc:date>
			<dc:type><xsl:value-of select="//*[local-name()='CoverageSubtype']" /></dc:type>
			<dc:format><xsl:value-of select="//*[local-name()='nativeFormat']" /></dc:format>
			<dc:identifier><xsl:value-of select="@id" /></dc:identifier>
			<dc:source><xsl:value-of select="//*[local-name()='dataSource']" /></dc:source>
			<dc:language></dc:language>
			<dc:relation></dc:relation>
			<dc:coverage></dc:coverage>
			<dc:rights></dc:rights>
		</oai_dc:dc>
	</xsl:template>

</xsl:stylesheet>