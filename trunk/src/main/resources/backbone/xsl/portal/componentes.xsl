<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
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
		<portal-vO xsi:type="java:mx.com.aon.portal.model.PortalVO">
			
			<xsl:if test="@CDCONFIGURA">
				<xsl:element name="clave-configura">
					<xsl:value-of select="@CDCONFIGURA" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONFIGURA">
				<xsl:element name="descripcion-configura">
					<xsl:value-of select="@DSCONFIGURA" />
				</xsl:element>
			</xsl:if>			
			<xsl:if test="@CDSECCION">
				<xsl:element name="clave-seccion">
					<xsl:value-of select="@CDSECCION" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDELEMENTO">
				<xsl:element name="clave-elemento">
					<xsl:value-of select="@CDELEMENTO" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSESPECIFICACION">
				<xsl:element name="especificacion">
					<xsl:value-of select="@DSESPECIFICACION" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONTENIDO">
				<xsl:element name="contenido">
					<xsl:value-of select="@DSCONTENIDO" />
				</xsl:element>
			</xsl:if>

			<xsl:if test="@CDTIPO">
				<xsl:element name="clave-tipo">
					<xsl:value-of select="@CDTIPO" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSARCHIVO">
				<xsl:element name="descripcion-archivo">
					<xsl:value-of select="@DSARCHIVO" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONTENIDOSEC">
				<xsl:element name="otro-contenido">
					<xsl:value-of select="@DSCONTENIDOSEC" />
				</xsl:element>
			</xsl:if>
		</portal-vO>
	</xsl:template>
</xsl:stylesheet>
