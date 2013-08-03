<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:template match="/">
        <ayuda-cobertura-cotizacion-vO xsi:type="java:mx.com.aon.flujos.cotizacion.model.AyudaCoberturaCotizacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </ayuda-cobertura-cotizacion-vO>

    </xsl:template>

    <xsl:template match="row">
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

        <xsl:if test="@DSAYUDA">
            <xsl:element name="ds-ayuda">
                <xsl:value-of select="@DSAYUDA" />
            </xsl:element>
        </xsl:if>
  </xsl:template>
</xsl:stylesheet>
