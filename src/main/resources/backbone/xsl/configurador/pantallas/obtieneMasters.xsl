<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.configurador.pantallas.model.MasterVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
            <xsl:if test="@CDTIPO">
                <xsl:element name="cd-tipo">
                    <xsl:value-of select="@CDTIPO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CDMASTER">
                <xsl:element name="cd-master">
                    <xsl:value-of select="@CDMASTER"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSMASTER">
                <xsl:element name="ds-master">
                    <xsl:value-of select="@DSMASTER"/>
                </xsl:element>
            </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>



</xsl:stylesheet>