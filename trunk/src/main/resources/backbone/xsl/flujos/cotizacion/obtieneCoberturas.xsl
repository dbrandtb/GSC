<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.CoberturaCotizacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDGARANT">
                    <xsl:element name="cd-garant">
                        <xsl:value-of select="@CDGARANT" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSGARANT">
                    <xsl:element name="ds-garant">
                        <xsl:value-of select="@DSGARANT" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@SUMASEG">
                    <xsl:element name="suma-asegurada">
                        <xsl:value-of select="@SUMASEG" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DEDUCIBLE">
                    <xsl:element name="deducible">
                        <xsl:value-of select="@DEDUCIBLE" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDCIAASEG">
                    <xsl:element name="cd-ciaaseg">
                        <xsl:value-of select="@CDCIAASEG" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                
             
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
