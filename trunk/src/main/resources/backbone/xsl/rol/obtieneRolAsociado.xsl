<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<rol-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.rol.model.RolVO"
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
		<xsl:if test="@CDRAMO">
			<xsl:element name="codigo-ramo">
				<xsl:value-of select="@CDRAMO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCOMPO">
			<xsl:element name="codigo-composicion">
				<xsl:value-of select="@CDCOMPO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCOMPO">
			<xsl:element name="descripcion-composicion">
				<xsl:value-of select="@DSCOMPO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMAXIMO">
			<xsl:element name="numero-maximo">
				<xsl:value-of select="@NMAXIMO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWDOMICI">
			<xsl:element name="switch-domicilio">
				<xsl:value-of select="@SWDOMICI" />
			</xsl:element>
		</xsl:if>
		
	</xsl:template>
</xsl:stylesheet>
