<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
        <suma-asegurada-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaVO">
        <xsl:if test="@CDRAMO">
			<xsl:element name="codigo-ramo">
				<xsl:value-of select="@CDRAMO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCAPITA">
			<xsl:element name="codigo-capital">
				<xsl:value-of select="@CDCAPITA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPCAP">
			<xsl:element name="codigo-tipo-capital">
				<xsl:value-of select="@CDTIPCAP"/>
			</xsl:element>
		</xsl:if>
		 <xsl:if test="@DSCAPITA">
			<xsl:element name="descripcion-capital">
				<xsl:value-of select="@DSCAPITA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDMONEDA">
			<xsl:element name="codigo-moneda">
				<xsl:value-of select="@CDMONEDA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSMONEDA">
			<xsl:element name="descripcion-moneda">
				<xsl:value-of select="@DSMONEDA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPCAP">
			<xsl:element name="descripcion-tipo-capital">
				<xsl:value-of select="@DSTIPCAP"/>
			</xsl:element>
		</xsl:if>
        </suma-asegurada-vO>
    </xsl:template>
</xsl:stylesheet>  