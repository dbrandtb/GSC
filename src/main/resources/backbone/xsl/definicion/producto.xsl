<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="/">
		<producto-vO
			xsi:type="java:mx.com.aon.catweb.configuracion.producto.definicion.model.ProductoVO"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</producto-vO>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@CDRAMO">
			<xsl:element name="codigo-ramo">
				<xsl:value-of select="@CDRAMO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSRAMO">
			<xsl:element name="descripcion-ramo">
				<xsl:value-of select="@DSRAMO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPRAM">
			<xsl:element name="codigo-tipo-parametro">
				<xsl:value-of select="@CDTIPRAM" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPRAM">
			<xsl:element name="descripcion-tipo-parametro">
				<xsl:value-of select="@DSTIPRAM" />
			</xsl:element>
		</xsl:if>

		<xsl:if test="@CDTIPORA">
			<xsl:element name="codigo-tipo-ramo">
				<xsl:value-of select="@CDTIPORA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPORA">
			<xsl:element name="descripcion-tipo-ramo">
				<xsl:value-of select="@DSTIPORA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWSUSCRI">
			<xsl:element name="switch-suscripcion">
				<xsl:value-of select="@SWSUSCRI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@TTIPCALC">
			<xsl:element name="tipo-calculo-polizas-temporales">
				<xsl:value-of select="@TTIPCALC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWREHABI">
			<xsl:element name="switch-rehabilitacion">
				<xsl:value-of select="@SWREHABI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@MMBENEFI">
			<xsl:element name="meses-beneficios">
				<xsl:value-of select="@MMBENEFI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWSINOMN">
			<xsl:element name="switch-permiso-pagos-otras-monedas">
				<xsl:value-of select="@SWSINOMN" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWREASEG">
			<xsl:element name="switch-reaseguro">
				<xsl:value-of select="@SWREASEG" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWTARIFA">
			<xsl:element name="switch-tarifa">
				<xsl:value-of select="@SWTARIFA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWREAUTO">
			<xsl:element name="switch-reinstalacion-automatica">
				<xsl:value-of select="@SWREAUTO" />
			</xsl:element>
		</xsl:if>

		<xsl:if test="@SWPRIUNI">
			<xsl:element name="switch-primas-unicas">
				<xsl:value-of select="@SWPRIUNI" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWINDPER">
			<xsl:element
				name="switch-distintas-polizas-por-asegurado">
				<xsl:value-of select="@SWINDPER" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWPOLDEC">
			<xsl:element name="switch-polizas-declarativas">
				<xsl:value-of select="@SWPOLDEC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWPRECAR">
			<xsl:element name="switch-preaviso-cartera">
				<xsl:value-of select="@SWPRECAR" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWTIPO">
			<xsl:element name="switch-tipo">
				<xsl:value-of select="@SWTIPO" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWSUBINC">
			<xsl:element name="switch-subincisos">
				<xsl:value-of select="@SWSUBINC" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSLINEA">
			<xsl:element name="descripcion">
				<xsl:value-of select="@DSLINEA" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPPOL">
			<xsl:element name="codigo-tipo-seguro">
				<xsl:value-of select="@CDTIPPOL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCALTIPPOL">
			<xsl:element name="codigo-tipo-poliza">
				<xsl:value-of select="@CDCALTIPPOL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWCANCEL">
			<xsl:element name="switch-cancelacion">
				<xsl:value-of select="@SWCANCEL" />
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWENDOSO">
			<xsl:element name="switch-endosos">
				<xsl:value-of select="@SWENDOSO" />
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
