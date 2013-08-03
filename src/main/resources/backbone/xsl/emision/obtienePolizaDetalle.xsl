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
        <rol-vO xsi:type="java:mx.com.aon.procesos.emision.model.PolizaDetVO">
		    <xsl:if test="@FEPROREN">
    			<xsl:element name="vigencia-hasta">
    				<xsl:value-of select="@FEPROREN"/>
    			</xsl:element>
    		</xsl:if>
            <xsl:if test="@FEEMISIO">
    			<xsl:element name="vigencia-desde">
    				<xsl:value-of select="@FEEMISIO"/>
    			</xsl:element>
    		</xsl:if>
            <xsl:if test="@DSMONEDA">
                <xsl:element name="moneda">
                    <xsl:value-of select="@DSMONEDA"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@FEEFECTO">
                <xsl:element name="fecha-efectividad">
                    <xsl:value-of select="@FEEFECTO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSPERPAG">
                <xsl:element name="periocidad">
                    <xsl:value-of select="@DSPERPAG"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@HHEFECTO">
                <xsl:element name="fecha-renovacion">
                    <xsl:value-of select="@HHEFECTO"/>
                </xsl:element>
            </xsl:if>
        </rol-vO>
    </xsl:template>
</xsl:stylesheet>        