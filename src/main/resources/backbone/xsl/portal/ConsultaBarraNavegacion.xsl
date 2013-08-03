<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/rows"/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<nav-item xsi:type="java:mx.com.aon.portal.model.ItemVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:apply-templates select="."/>
			</nav-item>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@ID">
			<xsl:element name="id">
				<xsl:value-of select="@ID"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@TEXT">
			<xsl:element name="texto">
				<xsl:value-of select="@TEXT"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
