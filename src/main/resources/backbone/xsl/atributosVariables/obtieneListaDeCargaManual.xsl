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
        <lista-de-valores-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.model.ListaDeValoresVO">
        <xsl:if test="@OTCLAVE">
			<xsl:element name="ot-clave">
				<xsl:value-of select="@OTCLAVE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTVALOR">
			<xsl:element name="ot-valor">
				<xsl:value-of select="@OTVALOR"/>
			</xsl:element>
		</xsl:if>
        </lista-de-valores-vO>
    </xsl:template>
</xsl:stylesheet>        