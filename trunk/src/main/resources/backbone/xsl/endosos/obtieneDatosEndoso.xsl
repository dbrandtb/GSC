<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="row">
        <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:if test="@ENDOSO">
                <xsl:element name="nm-endoso">
                    <xsl:value-of select="@ENDOSO" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSFORPAG">
                <xsl:element name="ds-forma-pag">
                    <xsl:value-of select="@DSFORPAG" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@TOTAL_A_PAGAR">
                <xsl:element name="total-pagar">
                    <xsl:value-of select="@TOTAL_A_PAGAR" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@MONTO_RECIBO">
                <xsl:element name="total-pagar-f">
                    <xsl:value-of select="@MONTO_RECIBO" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@NMRECSUB">
            	<xsl:element name="nmrecsub">
            		<xsl:value-of select="@NMRECSUB" />
            	</xsl:element>
            </xsl:if>
        </item-list>
    </xsl:template>
</xsl:stylesheet>
