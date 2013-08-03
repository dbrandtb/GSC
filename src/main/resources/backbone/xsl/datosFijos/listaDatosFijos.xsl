<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows "/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<dato-fijo-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.datosFijos.model.DatoFijoVO">
			<!--Strings-->
			<xsl:if test="@CDRAMO">
				<xsl:element name="codigo-ramo">
					<xsl:value-of select="@CDRAMO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDFUNCIO">
				<xsl:element name="codigo-funcio">
					<xsl:value-of select="@CDFUNCIO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDBLOQUE">
				<xsl:element name="descripcion-bloque">
					<xsl:value-of select="@CDBLOQUE"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDCAMPO">
				<xsl:element name="descripcion-campo">
					<xsl:value-of select="@CDCAMPO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDEXPRES">
				<xsl:element name="codigo-expresion">
					<xsl:value-of select="@CDEXPRES"/>
				</xsl:element>
			</xsl:if>
		</dato-fijo-vO>
	</xsl:template>
</xsl:stylesheet>

