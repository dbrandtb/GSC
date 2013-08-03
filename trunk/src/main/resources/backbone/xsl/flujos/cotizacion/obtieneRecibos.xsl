<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.ReciboVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
                 <xsl:if test="@ORIGEN">
                    <xsl:element name="origen">
                        <xsl:value-of select="@ORIGEN" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cdunieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nmpoliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMSITUAC">
                    <xsl:element name="nmsituac">
                        <xsl:value-of select="@NMSITUAC" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@IMPORTE_TOTAL">
                    <xsl:element name="importe-total">
                        <xsl:value-of select="@IMPORTE_TOTAL" />
                    </xsl:element>
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
