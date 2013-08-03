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
        <datos-clave-atributo-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DatosClaveAtributoVO">
 
        <xsl:if test="@DSCLAVE1">
			<xsl:element name="descripcion">
				<xsl:value-of select="@DSCLAVE1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWFORMA1">
			<xsl:element name="formato">
				<xsl:value-of select="@SWFORMA1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSFORMA1">
			<xsl:element name="descripcion-formato">
				<xsl:value-of select="@DSFORMA1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMIN1">
			<xsl:element name="minimo">
				<xsl:value-of select="@NMLMIN1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMLMAX1">
			<xsl:element name="maximo">
				<xsl:value-of select="@NMLMAX1"/>
			</xsl:element>
		</xsl:if>
		
        </datos-clave-atributo-vO>
    </xsl:template>
</xsl:stylesheet>  