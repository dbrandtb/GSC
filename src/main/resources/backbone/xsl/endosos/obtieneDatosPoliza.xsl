<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <datos-poliza-vO xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </datos-poliza-vO>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.flujos.endoso.model.DatosPolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDPERPAG">
        			<xsl:element name="cdperpag">
        				<xsl:value-of select="@CDPERPAG"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDMONEDA">
        			<xsl:element name="cdmoneda">
        				<xsl:value-of select="@CDMONEDA"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@OTTEMPOT">
        			<xsl:element name="ottempot">
        				<xsl:value-of select="@OTTEMPOT"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@NMRENOVA">
        			<xsl:element name="nmrenova">
        				<xsl:value-of select="@NMRENOVA"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@NMPOLIEX">
        			<xsl:element name="nmpoliex">
        				<xsl:value-of select="@NMPOLIEX"/>
        			</xsl:element>
        		</xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 