<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S' "-->
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
            <!--/xsl:when>
                <xsl:otherwise>
                <xsl:call-template name="error" />
                </xsl:otherwise>
                </xsl:choose-->
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value" />
            </xsl:element>

            <xsl:element name="cd-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value" />
            </xsl:element>
            <xsl:element name="ds-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value" />
            </xsl:element>

        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.ImporteReaseguroGrupoCoberturaTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDGRUPO">
                    <xsl:element name="cdgrupo">
                        <xsl:value-of select="@CDGRUPO" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDRAREAS">
                    <xsl:element name="cdrareas">
                        <xsl:value-of select="@CDRAREAS" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@INCONTRA">
                    <xsl:element name="incontra">
                        <xsl:value-of select="@INCONTRA" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDEXPNVR">
                    <xsl:element name="cdexpnvr">
                        <xsl:value-of select="@CDEXPNVR" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDEXPCP1">
                    <xsl:element name="cdexpcp1">
                        <xsl:value-of select="@CDEXPCP1" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDEXPCP2">
                    <xsl:element name="cdexpcp2">
                        <xsl:value-of select="@CDEXPCP2" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDTIPCNR">
                    <xsl:element name="cdtipcnr">
                        <xsl:value-of select="@CDTIPCNR" />
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