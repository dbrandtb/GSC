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
		<clave-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.expresiones.model.ClaveVO">
			<!--Strings-->
			<xsl:if test="@CDEXPRES">
				<xsl:element name="codigo-expresion">
					<xsl:value-of select="@CDEXPRES"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDVARIAB">
				<xsl:element name="codigo-variable">
					<xsl:value-of select="@CDVARIAB"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="OTTABLA">
				<xsl:element name="tabla">
					<xsl:value-of select="OTTABLA"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="OTSELECT">
				<xsl:element name="columna">
					<xsl:value-of select="OTSELECT"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="CDEXPRES_KEY">
				<xsl:element name="codigo-expresion-key">
					<xsl:value-of select="CDEXPRES_KEY"/>
				</xsl:element>
			</xsl:if>
		</clave-vO>
	</xsl:template>
</xsl:stylesheet>

