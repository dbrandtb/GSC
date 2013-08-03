<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/rows[@id='count']"/>
			<xsl:apply-templates select="result/rows[@id='consulta']"/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<nav-item xsi:type="java:mx.com.aon.test.model.ReciboVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:apply-templates select="."/>
			</nav-item>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@COUNT">
			<xsl:element name="count">
				<xsl:value-of select="@COUNT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMPOLIZA">
			<xsl:element name="nmpoliza">
				<xsl:value-of select="@NMPOLIZA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMSUPLEM">
			<xsl:element name="nmsuplem">
				<xsl:value-of select="@NMSUPLEM"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMRECIBO">
			<xsl:element name="nmrecibo">
				<xsl:value-of select="@NMRECIBO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FEINICIO">
			<xsl:element name="feinicio">
				<xsl:value-of select="@FEINICIO"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>