<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
        <expresion-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.expresiones.model.ExpresionVO">
        <xsl:if test="@CDEXPRES">
			<xsl:element name="codigo-expresion">
				<xsl:value-of select="@CDEXPRES"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTEXPRES">
			<xsl:element name="ot-expresion">
				<xsl:value-of select="@OTEXPRES"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWRECALC">
			<xsl:element name="switch-recalcular">
				<xsl:value-of select="@SWRECALC"/>
			</xsl:element>
		</xsl:if>
        </expresion-vO>
    </xsl:template>
</xsl:stylesheet>        