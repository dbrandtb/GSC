<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
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
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.DatoAnticipoTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@cdtipant">
                    <xsl:element name="cdtipant">
                        <xsl:value-of select="@cdtipant" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@cdtipsit">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@cdtipsit" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@mmperint">
                    <xsl:element name="mmperint">
                        <xsl:value-of select="@mmperint" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@cdexpant">
                    <xsl:element name="cdexpant">
                        <xsl:value-of select="@cdexpant" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@cdexpint">
                    <xsl:element name="cdexpint">
                        <xsl:value-of select="@cdexpint" />
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