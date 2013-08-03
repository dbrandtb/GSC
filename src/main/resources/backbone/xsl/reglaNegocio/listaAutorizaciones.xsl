<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
     <xsl:template match="/">
	 <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		<xsl:element name="msg-id">
  			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" /> 
  		</xsl:element>
 		<xsl:element name="msg">
  			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" /> 
  		</xsl:element>
  		<xsl:apply-templates select="result/storedProcedure/outparam/rows" /> 
  	</wrapper-resultados>
  </xsl:template>
  
     <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.catweb.configuracion.producto.reglanegocio.model.ReglaNegocioVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

			<xsl:if test="@CDMOTIVO">
				<xsl:element name="nombre">
					<xsl:value-of select="@CDMOTIVO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSMOTIVO">
				<xsl:element name="descripcion">
					<xsl:value-of select="@DSMOTIVO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@OTNIVEL">
				<xsl:element name="nivel">
					<xsl:value-of select="@OTNIVEL"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDEXPRES">
				<xsl:element name="codigo-expresion">
					<xsl:value-of select="@CDEXPRES"/>
				</xsl:element>
			</xsl:if>
				
          </item-list>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>