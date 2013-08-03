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
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="." />
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<conceptos-cobertura-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.conceptosCobertura.model.ConceptosCoberturaVO">
			<xsl:if test="@CDPERIOD">
				<xsl:element name="codigo-periodo">
					<xsl:value-of select="@CDPERIOD" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSFECHAS">
				<xsl:element name="descripcion-periodo">
					<xsl:value-of select="@DSFECHAS" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@ORDEN">
				<xsl:element name="orden">
					<xsl:value-of select="@ORDEN" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDGARANT">
				<xsl:element name="codigo-cobertura">
					<xsl:value-of select="@CDGARANT" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSGARANT">
				<xsl:element name="descripcion-cobertura">
					<xsl:value-of select="@DSGARANT" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDCONTAR">
				<xsl:element name="codigo-concepto">
					<xsl:value-of select="@CDCONTAR" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONTAR">
				<xsl:element name="descripcion-concepto">
					<xsl:value-of select="@DSCONTAR" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@OTTIPO">
				<xsl:element name="codigo-comportamiento">
					<xsl:value-of select="@OTTIPO" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCOMPOR">
				<xsl:element name="descripcion-comportamiento">
					<xsl:value-of select="@DSCOMPOR" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDCONDIC">
				<xsl:element name="codigo-condicion">
					<xsl:value-of select="@CDCONDIC" />
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONDIC">
				<xsl:element name="descripcion-condicion">
					<xsl:value-of select="@DSCONDIC" />
				</xsl:element>
			</xsl:if>
		</conceptos-cobertura-vO>
	</xsl:template>
</xsl:stylesheet>
