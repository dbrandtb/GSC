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
            
            <!-- temporal -->
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
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.CompSumasAseguradasTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>

                </xsl:if>
                <xsl:if test="@CDCAPITA">
                    <xsl:element name="cdcapita">
                        <xsl:value-of select="@CDCAPITA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPCAP">
                    <xsl:element name="cdtipcap">
                        <xsl:value-of select="@CDTIPCAP" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWOBLIGA">
                    <xsl:element name="swobliga">
                        <xsl:value-of select="@SWOBLIGA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWINSERT">
                    <xsl:element name="swinsert">
                        <xsl:value-of select="@SWINSERT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPDEF">
                    <xsl:element name="cdexpdef">
                        <xsl:value-of select="@CDEXPDEF" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPRED">
                    <xsl:element name="cdexpred">
                        <xsl:value-of select="@CDEXPRED" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPCRD">
                    <xsl:element name="cdexpcrd">
                        <xsl:value-of select="@CDEXPCRD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPRSC">
                    <xsl:element name="cdexprsc">
                        <xsl:value-of select="@CDEXPRSC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEXPRRD">
                    <xsl:element name="cdexprrd">
                        <xsl:value-of select="@CDEXPRRD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@cdexpspr">
                    <xsl:element name="cdexpspr">
                        <xsl:value-of select="@cdexpspr" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@cdcondic">
                    <xsl:element name="cdcondic">
                        <xsl:value-of select="@cdcondic" />
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