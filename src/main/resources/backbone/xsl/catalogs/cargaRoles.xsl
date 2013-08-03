<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows" />
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="." />
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<roles-vO xsi:type="java:mx.com.aon.portal.model.principal.RolesVO">
			<xsl:if test="@CDROL">
				<xsl:element name="cd-rol">
					<xsl:value-of select="@CDROL"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSROL">
				<xsl:element name="ds-rol">
					<xsl:value-of select="@DSROL"/>
				</xsl:element>
			</xsl:if>
		</roles-vO>
	</xsl:template>
</xsl:stylesheet>