<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
			<!-- Aqui hay que seleccionar el parametro de salida de tipo cursor que queremos tomar -->
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="." />
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<inciso-vO
			xsi:type="java:mx.com.aon.portal.model.ClientesCorpoVO">
<!-- Aqui ponemos nuestro VO -->
			<xsl:if test="@CDELEMENTO">
				<xsl:element name="cd-elemento">
					<xsl:value-of select="@CDELEMENTO" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSELEMEN">
				<xsl:element name="ds-elemen">
					<xsl:value-of select="@DSELEMEN" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDPERSON">
				<xsl:element name="cd-person">
					<xsl:value-of select="@CDPERSON" />
				</xsl:element>
			</xsl:if>
		</inciso-vO>
	</xsl:template>
</xsl:stylesheet>