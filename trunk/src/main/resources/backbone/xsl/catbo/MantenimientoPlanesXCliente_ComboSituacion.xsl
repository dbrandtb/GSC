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
			xsi:type="java:mx.com.aon.portal.model.TiposSituacionVO">
<!-- Aqui ponemos nuestro VO -->
			<xsl:if test="@CDTIPSIT">
				<xsl:element name="cd-tip-sit">
					<xsl:value-of select="@CDTIPSIT" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSTIPSIT">
				<xsl:element name="ds-tip-sit">
					<xsl:value-of select="@DSTIPSIT" />
				</xsl:element>
			</xsl:if>
		</inciso-vO>
	</xsl:template>
</xsl:stylesheet>
