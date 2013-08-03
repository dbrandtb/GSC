<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--xsl:choose-->
                <!--xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'" -->
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
                <!--/xsl:when-->
                <!--xsl:otherwise>
                    <xsl:call-template name="error" />
                </xsl:otherwise-->
            <!--/xsl:choose-->
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value" />
            </xsl:element>
            
            <!-- TEMPORALMENTE -->
            <xsl:element name="cd-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value" />
            </xsl:element>
            <xsl:element name="ds-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value" />
            </xsl:element>            
            <!-- TEMPORALEMENTE -->
            
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">        
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.ExpresionTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDEXPRES">
                    <!--key xsi:type="java:java.lang.String">cdexpres</key-->
                    <xsl:element name="cdexpres">
                        <xsl:value-of select="@CDEXPRES" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTIPEXP">
                    <!--key xsi:type="java:java.lang.String">ottipexp</key-->
                    <xsl:element name="ottipexp">
                        <xsl:value-of select="@OTTIPEXP" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTIPORG">
                    <!--key xsi:type="java:java.lang.String">ottiporg</key-->
                    <xsl:element name="ottiporg">
                        <xsl:value-of select="@OTTIPORG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWRECALC">
                    <!--key xsi:type="java:java.lang.String">swrecalc</key-->
                    <xsl:element name="swrecalc">
                        <xsl:value-of select="@SWRECALC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTLENGTH">
                    <!--key xsi:type="java:java.lang.String">otlength</key-->
                    <xsl:element name="otlength">
                        <xsl:value-of select="@OTLENGTH" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTDEPTH">
                    <!--key xsi:type="java:java.lang.String">otdepth</key-->
                    <xsl:element name="otdepth">
                        <xsl:value-of select="@OTDEPTH" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTEXPRE">
                    <!--key xsi:type="java:java.lang.String">otexpres</key-->
                    <xsl:element name="otexpres">
                        <xsl:value-of select="@OTEXPRE" />
                    </xsl:element>
                </xsl:if>

            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value" />
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value" />
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>