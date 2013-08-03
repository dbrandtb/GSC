<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error" />
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value" />
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.royalsun.vo.TomadorAgrupadorVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDTIPIDE">
                    <xsl:element name="cdtipide">
                        <xsl:value-of select="@CDTIPIDE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDIDEPER">
                    <xsl:element name="cdideper">
                        <xsl:value-of select="@CDIDEPER" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="dsnombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDROL">
                    <xsl:element name="cdrol">
                        <xsl:value-of select="@CDROL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSROL">
                    <xsl:element name="dsrol">
                        <xsl:value-of select="@DSROL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMORDDOM">
                    <xsl:element name="nmorddom">
                        <xsl:value-of select="@NMORDDOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSITUAC">
                    <xsl:element name="nmsituac">
                        <xsl:value-of select="@NMSITUAC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDIDEINC">
                    <xsl:element name="cdideinc">
                        <xsl:value-of select="@CDIDEINC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDAGRUPA">
                    <xsl:element name="cdagrupa">
                        <xsl:value-of select="@CDAGRUPA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cdperson">
                        <xsl:value-of select="@CDPERSON" />
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
