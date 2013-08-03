<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<rol-vO
			xsi:type="mx.com.aon.flujos.cotizacion.model.RolVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</rol-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@CDROL">
			<xsl:element name="codigo-rol">
				<xsl:value-of select="@CDROL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSROL">
			<xsl:element name="descripcion-rol">
				<xsl:value-of select="@DSROL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDPERSON">
			<xsl:element name="codigo-persona">
				<xsl:value-of select="@CDPERSON" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSNOMBRE">
			<xsl:element name="descripcion-nombre">
				<xsl:value-of select="@DSNOMBRE" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDRFC">
			<xsl:element name="cd-rfc">
				<xsl:value-of select="@CDRFC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FENACIMI">
			<xsl:element name="fecha-nacimiento">
				<xsl:value-of select="@FENACIMI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTFISJUR">
			<xsl:element name="ot-fis-jur">
				<xsl:value-of select="@OTFISJUR" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSFISJUR">
			<xsl:element name="ds-fis-jur">
				<xsl:value-of select="@DSFISJUR" />
			</xsl:element>
		</xsl:if>		
		
	</xsl:template>
</xsl:stylesheet>
