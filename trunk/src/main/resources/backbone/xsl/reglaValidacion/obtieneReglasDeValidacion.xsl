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
        <regla-validacion-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.reglaValidacion.model.ReglaValidacionVO">
        <xsl:if test="@CDBLOQUE">
			<xsl:element name="codigo-bloque">
				<xsl:value-of select="@CDBLOQUE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSBLOQUE">
			<xsl:element name="descripcion-bloque">
				<xsl:value-of select="@DSBLOQUE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTSECUEN">
			<xsl:element name="secuencia">
				<xsl:value-of select="@OTSECUEN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDVALIDA">
			<xsl:element name="codigo-validacion">
				<xsl:value-of select="@CDVALIDA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSVALIDA">
			<xsl:element name="descripcion-validacion">
				<xsl:value-of select="@DSVALIDA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCONDIC">
			<xsl:element name="codigo-condicion">
				<xsl:value-of select="@CDCONDIC"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCONDIC">
			<xsl:element name="descripcion-condicion">
				<xsl:value-of select="@DSCONDIC"/>
			</xsl:element>
		</xsl:if>
        </regla-validacion-vO>
    </xsl:template>
</xsl:stylesheet>  