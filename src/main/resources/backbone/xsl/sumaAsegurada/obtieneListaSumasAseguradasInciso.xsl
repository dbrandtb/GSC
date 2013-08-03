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
        <suma-asegurada-inciso-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.sumaAsegurada.model.SumaAseguradaIncisoVO">
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
		<xsl:if test="@DSCAPITA">
			<xsl:element name="descripcion-capital">
				<xsl:value-of select="@DSCAPITA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPCAP">
			<xsl:element name="codigo-tipo-capital">
				<xsl:value-of select="@CDTIPCAP"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPCAP">
			<xsl:element name="descripcion-tipo-capital">
				<xsl:value-of select="@DSTIPCAP"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDGARANT">
			<xsl:element name="codigo-cobertura">
				<xsl:value-of select="@CDGARANT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSGARANT">
			<xsl:element name="descripcion-cobertura">
				<xsl:value-of select="@DSGARANT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDPRESEN">
			<xsl:element name="codigo-leyenda">
				<xsl:value-of select="@CDPRESEN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEYENDA">
			<xsl:element name="descripcion-leyenda">
				<xsl:value-of select="@LEYENDA"/>
			</xsl:element>
		</xsl:if>		
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="codigo-tipo-situacion">
				<xsl:value-of select="@CDTIPSIT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPSIT">
			<xsl:element name="descripcion-tipo-situacion">
				<xsl:value-of select="@DSTIPSIT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWREAUTO">
			<xsl:element name="switch-reinstalacion">
				<xsl:value-of select="@SWREAUTO"/>
			</xsl:element>
		</xsl:if>		
		<xsl:if test="@OTTABVAL">
			<xsl:element name="codigo-lista-valor">
				<xsl:value-of select="@OTTABVAL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTABLA">
			<xsl:element name="descripcion-lista-valor">
				<xsl:value-of select="@DSTABLA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDEXPDEF">
			<xsl:element name="codigo-expresion">
				<xsl:value-of select="@CDEXPDEF"/>
			</xsl:element>
		</xsl:if>
				
        </suma-asegurada-inciso-vO>
    </xsl:template>
</xsl:stylesheet>  