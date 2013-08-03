<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
      </wrapper-resultados>
            
 </xsl:template>
    <xsl:template match="row">
			<item-list
				xsi:type="java:mx.com.aon.configurador.pantallas.model.PantallaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		           <xsl:if test="@CDPANTALLA">
		                <xsl:element name="cd-pantalla">
		                    <xsl:value-of select="@CDPANTALLA"/>
		                </xsl:element>
		            </xsl:if>
		            
		            <xsl:if test="@DSNOMBREPANTALLA">
		                <xsl:element name="nombre-pantalla">
		                    <xsl:value-of select="@DSNOMBREPANTALLA"/>
		                </xsl:element>
		            </xsl:if>
		            
		             <xsl:if test="@CDMASTER">
		                <xsl:element name="cd-master">
		                    <xsl:value-of select="@CDMASTER"/>
		                </xsl:element>
		            </xsl:if>
		            
		            <xsl:if test="@DSDESCPANTALLA">
		                <xsl:element name="descripcion">
		                    <xsl:value-of select="@DSDESCPANTALLA"/>
		                </xsl:element>
		            </xsl:if>
		            
		            
		           
		            
		            <xsl:if test="@DSARCHIVO">
		                <xsl:element name="ds-archivo">
		                    <xsl:value-of select="@DSARCHIVO"/>
		                </xsl:element>
		            </xsl:if>

		            <xsl:if test="@CDSISROL">
		                <xsl:element name="cd-rol">
		                    <xsl:value-of select="@CDSISROL"/>
		                </xsl:element>
		            </xsl:if>

		            <xsl:if test="@DSSISROL">
		                <xsl:element name="ds-rol">
		                    <xsl:value-of select="@DSSISROL"/>
		                </xsl:element>
		            </xsl:if>
           </item-list>
    </xsl:template>
</xsl:stylesheet>