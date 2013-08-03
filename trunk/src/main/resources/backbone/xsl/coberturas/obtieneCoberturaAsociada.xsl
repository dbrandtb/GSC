<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <cobertura-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.coberturas.model.CoberturaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </cobertura-vO>
    </xsl:template>
    <xsl:template match="row">
  <xsl:if test="@CDGARANT">
   <xsl:element name="clave-cobertura">
    <xsl:value-of select="@CDGARANT"/>
   </xsl:element>
  </xsl:if>
  <xsl:if test="@DSGARANT">
   <xsl:element name="descripcion-cobertura">
    <xsl:value-of select="@DSGARANT"/>
   </xsl:element>
  </xsl:if>
  <xsl:if test="@SWCAPITA">
   <xsl:element name="suma-asegurada">
    <xsl:value-of select="@SWCAPITA"/>
   </xsl:element>
  </xsl:if>
  <xsl:if test="@CDCAPITA">
   <xsl:element name="codigo-capital">
    <xsl:value-of select="@CDCAPITA"/>
   </xsl:element>
  </xsl:if> 
  <xsl:if test="@DSCAPITA">
   <xsl:element name="descripcion-capital">
    <xsl:value-of select="@DSCAPITA"/>
   </xsl:element>
  </xsl:if> 
  <xsl:if test="@SWOBLIGA">
   <xsl:element name="obligatorio">
    <xsl:value-of select="@SWOBLIGA"/>
   </xsl:element>
  </xsl:if> 
  <xsl:if test="@SWINSERT">
   <xsl:element name="inserta">
    <xsl:value-of select="@SWINSERT"/>
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
  
    </xsl:template>
</xsl:stylesheet>
