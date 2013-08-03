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
        <clausula-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.definicion.model.ClausulaVO">
         <xsl:if test="@CDRAMO">
			<xsl:element name="codigo-ramo">
				<xsl:value-of select="@CDRAMO"/>
			</xsl:element>
		</xsl:if>
        <xsl:if test="@CDCLAUSU">
			<xsl:element name="codigo-clausula">
				<xsl:value-of select="@CDCLAUSU"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCLAUSU">
			<xsl:element name="descripcion-clausula">
				<xsl:value-of select="@DSCLAUSU"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSLINEA">
			<xsl:element name="descripcion-linea">
				<xsl:value-of select="@DSLINEA"/>
			</xsl:element>
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="codigo-tipo-inciso">
				<xsl:value-of select="@CDTIPSIT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPSIT">
			<xsl:element name="descripcion-tipo-inciso">
				<xsl:value-of select="@DSTIPSIT"/>
			</xsl:element>
		</xsl:if>
		</xsl:if>
        </clausula-vO>
    </xsl:template>
</xsl:stylesheet>   