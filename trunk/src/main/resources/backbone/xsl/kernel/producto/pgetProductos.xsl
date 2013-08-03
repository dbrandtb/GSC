<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.vo.ProductoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDRAMO">                    
                        <xsl:element name="cd-ramo">
                            <xsl:value-of select="@CDRAMO"/>
                        </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">                    
                        <xsl:element name="ds-ramo">
                            <xsl:value-of select="@DSRAMO"/>
                        </xsl:element>
                </xsl:if>
                <xsl:if test="@FEPRINCP">
                        <xsl:element name="fecha-prinp">
                            <xsl:value-of select="@FEPRINCP"/>
                        </xsl:element>                    
                </xsl:if>
                <xsl:if test="@FEFINALP">
                        <xsl:element name="fecha-fin">
                            <xsl:value-of select="@FEFINALP"/>
                        </xsl:element>
                </xsl:if>
            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>