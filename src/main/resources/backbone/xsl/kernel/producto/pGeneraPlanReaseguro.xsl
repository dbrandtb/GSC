<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S' "-->
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
            <!--/xsl:when>
                <xsl:otherwise>
                <xsl:call-template name="error"/>
                </xsl:otherwise>
                </xsl:choose-->
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value" />
            </xsl:element>
            
            <!-- TEMPORAL  -->
            <xsl:element name="cd-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value" />
            </xsl:element>
            <xsl:element name="ds-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value" />
            </xsl:element>
            <!-- TEMPORAL -->

        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.GrupoPlanesReaseguroTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDGRUPO">
                    <xsl:element name="cdgrupo">
                        <xsl:value-of select="@CDGRUPO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPLAN">
                    <xsl:element name="cdplan">
                        <xsl:value-of select="@CDPLAN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPPLA">
                    <xsl:element name="cdtippla">
                        <xsl:value-of select="@CDTIPPLA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPCUM">
                    <xsl:element name="cdtipcum">
                        <xsl:value-of select="@CDTIPCUM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMONEDA">
                    <xsl:element name="cdmoneda">
                        <xsl:value-of select="@CDMONEDA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWMONORI">
                    <xsl:element name="swmonori">
                        <xsl:value-of select="@SWMONORI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEINICIO">
                    <xsl:element name="feinicio">
                        <xsl:value-of select="@FEINICIO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEFINAL">
                    <xsl:element name="fefinal">
                        <xsl:value-of select="@FEFINAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMORDEN">
                    <xsl:element name="nmorden">
                        <xsl:value-of select="@NMORDEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPRES">
                    <xsl:element name="cdexpres">
                        <xsl:value-of select="@CDEXPRES" />
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