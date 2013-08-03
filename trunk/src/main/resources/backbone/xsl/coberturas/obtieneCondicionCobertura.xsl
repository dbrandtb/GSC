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
        <llave-valor-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.model.LlaveValorVO">
        <xsl:if test="@CDCONDIC">
			<xsl:element name="key">
				<xsl:value-of select="@CDCONDIC"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCONDIC">
			<xsl:element name="value">
				<xsl:value-of select="@DSCONDIC"/>
			</xsl:element>
		</xsl:if>
        </llave-valor-vO>
    </xsl:template>
</xsl:stylesheet>        