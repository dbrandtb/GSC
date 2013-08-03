<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

    <xsl:template match="/">
        <master-vO xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.IncisosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMSITUAC">
        			<xsl:element name="ds-cobertura">
        				<xsl:value-of select="@NMSITUAC"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDROL">
        			<xsl:element name="ds-suma-asegurada">
        				<xsl:value-of select="@CDROL"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="ds-deducible">
                        <xsl:value-of select="@CDPERSON"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 