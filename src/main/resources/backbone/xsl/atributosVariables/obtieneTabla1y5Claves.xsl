<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<lista-de-valores-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.model.ListaDeValoresVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</lista-de-valores-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@NMTABLA">
			<xsl:element name="numero-tabla">
				<xsl:value-of select="@NMTABLA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTABLA">
			<xsl:element name="nombre">
				<xsl:value-of select="@CDTABLA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLA">
			<xsl:element name="descripcion">
				<xsl:value-of select="@DSTABLA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CLNATURA">
			<xsl:element name="clnatura">
				<xsl:value-of select="@CLNATURA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSNATURA">
			<xsl:element name="ds-natura">
				<xsl:value-of select="@DSNATURA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTTIPOAC">
			<xsl:element name="ottipoac">
				<xsl:value-of select="@OTTIPOAC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWMODIFI">
			<xsl:element name="modifica-valores">
				<xsl:value-of select="@SWMODIFI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWERROR">
			<xsl:element name="enviar-tabla-errores">
				<xsl:value-of select="@SWERROR" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTABLJ1">
			<xsl:element name="cd-catalogo1">
				<xsl:value-of select="@CDTABLJ1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTABLJ2">
			<xsl:element name="cd-catalogo2">
				<xsl:value-of select="@CDTABLJ2" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTABLJ3">
			<xsl:element name="clave-dependencia">
				<xsl:value-of select="@CDTABLJ3" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLJ1">
			<xsl:element name="ds-catalogo1">
				<xsl:value-of select="@DSTABLJ1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLJ2">
			<xsl:element name="ds-catalogo2">
				<xsl:value-of select="@DSTABLJ2" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCLAVE1">
			<xsl:element name="clave">
				<xsl:value-of select="@DSCLAVE1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWFORMA1">
			<xsl:element name="formato-clave">
				<xsl:value-of select="@SWFORMA1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMIN1">
			<xsl:element name="minimo-clave">
				<xsl:value-of select="@NMLMIN1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMAX1">
			<xsl:element name="maximo-clave">
				<xsl:value-of select="@NMLMAX1" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDATRIBU">
			<xsl:element name="cd-atribu">
				<xsl:value-of select="@CDATRIBU" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSATRIBU">
			<xsl:element name="descripcion-formato">
				<xsl:value-of select="@DSATRIBU" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWFORMAT">
			<xsl:element name="formato-descripcion">
				<xsl:value-of select="@SWFORMAT" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMIN">
			<xsl:element name="minimo-descripcion">
				<xsl:value-of select="@NMLMIN" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMAX">
			<xsl:element name="maximo-descripcion">
				<xsl:value-of select="@NMLMAX" />
			</xsl:element>
		</xsl:if>

	</xsl:template>
</xsl:stylesheet>
