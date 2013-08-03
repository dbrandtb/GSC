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
        <datos-entrada-cotiza-vO xsi:type="java:mx.com.aon.flujos.cotizacion.model.DatosEntradaCotizaVO">
			<xsl:if test="@DSATRIBU">
				<xsl:element name="ds-atribu">
					<xsl:value-of select="@DSATRIBU"/>
				</xsl:element>
			</xsl:if>	
			<xsl:if test="@OTVALOR">
				<xsl:element name="ot-valor">
					<xsl:value-of select="@OTVALOR"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSNOMBRE">
				<xsl:element name="ds-nombre">
					<xsl:value-of select="@DSNOMBRE"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSVALOR">
				<xsl:element name="ds-valor">
					<xsl:value-of select="@DSVALOR"/>
				</xsl:element>
			</xsl:if>
        </datos-entrada-cotiza-vO>
    </xsl:template>
</xsl:stylesheet>