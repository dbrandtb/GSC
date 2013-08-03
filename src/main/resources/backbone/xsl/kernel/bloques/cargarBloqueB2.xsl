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
            <cursor xsi:type="java:mx.com.ice.services.to.BloqueVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

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

                <xsl:if test="@CDAGENTE">
                    <xsl:element name="cdagente">
                        <xsl:value-of select="@CDAGENTE" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDTIPOAG">
                    <xsl:element name="cdtipoag">
                        <xsl:value-of select="@CDTIPOAG" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@PORREDAU">
                    <xsl:element name="porredau">
                        <xsl:value-of select="@PORREDAU" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NTSEXO">
                    <xsl:element name="ntsexo">
                        <xsl:value-of select="@NTSEXO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDTIPPER">
                    <xsl:element name="cdtipper">
                        <xsl:value-of select="@CDTIPPER" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NTFISJUR">
                    <xsl:element name="ntfisjur">
                        <xsl:value-of select="@NTFISJUR" />
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
