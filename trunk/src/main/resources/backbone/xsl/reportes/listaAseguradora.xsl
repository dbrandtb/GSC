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
        <combografico-vO xsi:type="java:mx.com.aon.portal.model.reporte.ComboGraficoVo">
            <xsl:if test="@CDUNIECO">
                <xsl:element name="codigo">
                    <xsl:value-of select="@CDUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSUNIECO">
                <xsl:element name="descrip-c">
                    <xsl:value-of select="@DSUNIECO"/>
                </xsl:element>
            </xsl:if>                  
        </combografico-vO>
    </xsl:template>
</xsl:stylesheet>   