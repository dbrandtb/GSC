<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<base-object-vO
			xsi:type="mx.com.aon.portal.model.BaseObjectVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</base-object-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@NMSUPLEM">
			<xsl:element name="value">
				<xsl:value-of select="@NMSUPLEM" />
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
