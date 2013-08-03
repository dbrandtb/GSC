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
        <situacion-vO xsi:type="java:mx.com.aon.flujos.cotizacion.model.SituacionVO">
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="cd-tipsit">
				<xsl:value-of select="@CDTIPSIT"/>
			</xsl:element>
		</xsl:if>	
		<xsl:if test="@CDASEGUR">
			<xsl:element name="comp-asegur">
				<xsl:value-of select="@CDASEGUR"/>
			</xsl:element>
		</xsl:if>	
		<xsl:if test="@CDPLAN">
			<xsl:element name="cd-plan">
				<xsl:value-of select="@CDPLAN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMSITUAC">
			<xsl:element name="nm-situac">
				<xsl:value-of select="@NMSITUAC"/>
			</xsl:element>
		</xsl:if>
        </situacion-vO>
    </xsl:template>
</xsl:stylesheet>