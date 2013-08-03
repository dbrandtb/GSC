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
        <descripcion-cinco-claves-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.DescripcionCincoClavesVO">
        
        <xsl:if test="@OTCLAVE1">
			<xsl:element name="descripcion-clave1">
				<xsl:value-of select="@OTCLAVE1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTCLAVE2">
			<xsl:element name="descripcion-clave2">
				<xsl:value-of select="@OTCLAVE2"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTCLAVE3">
			<xsl:element name="descripcion-clave3">
				<xsl:value-of select="@OTCLAVE3"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTCLAVE4">
			<xsl:element name="descripcion-clave4">
				<xsl:value-of select="@OTCLAVE4"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTCLAVE5">
			<xsl:element name="descripcion-clave5">
				<xsl:value-of select="@OTCLAVE5"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FEDESDE">
			<xsl:element name="fecha-desde">
				<xsl:value-of select="@FEDESDE"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FEHASTA">
			<xsl:element name="fecha-hasta">
				<xsl:value-of select="@FEHASTA"/>
			</xsl:element>
		</xsl:if>
        </descripcion-cinco-claves-vO>
    </xsl:template>
</xsl:stylesheet>        