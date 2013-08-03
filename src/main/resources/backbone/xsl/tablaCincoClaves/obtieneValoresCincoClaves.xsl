<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <valores-cinco-claves-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.tablaCincoClaves.model.ValoresCincoClavesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        
        <xsl:if test="@FEHASTA">
   <xsl:element name="fecha-hasta">
    <xsl:value-of select="@FEHASTA"/>
   </xsl:element>
  </xsl:if>
        
        </valores-cinco-claves-vO>
    </xsl:template>
    
    <xsl:template match="row">
    
  
  
    </xsl:template>
    
    <xsl:template match="row">
    
  
  
    </xsl:template>
    
    
</xsl:stylesheet>
