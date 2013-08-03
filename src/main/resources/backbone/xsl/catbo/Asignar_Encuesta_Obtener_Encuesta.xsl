<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
            <item-list xsi:type="java:mx.com.aon.catbo.model.AsignarEncuestaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		       
		       	<xsl:if test="@NMCONFIG">
                    <xsl:element name="nm-config">
                        <xsl:value-of select="@NMCONFIG" />
                    </xsl:element>
                </xsl:if>
                
		       	<xsl:if test="@DSUNIECO">
                    <xsl:element name="ds-unieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                   
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                               
                <xsl:if test="@DSRAMO">
		            <xsl:element name="ds-ramo">
		                <xsl:value-of select="@DSRAMO" />
		            </xsl:element>
		        </xsl:if>
                
                 <xsl:if test="@CDMODULO">
		            <xsl:element name="cd-modulo">
		                <xsl:value-of select="@CDMODULO" />
		            </xsl:element>
		        </xsl:if>
		        
				<xsl:if test="@CDRAMO">
		            <xsl:element name="cd-ramo">
		                <xsl:value-of select="@CDRAMO" />
		            </xsl:element>
		        </xsl:if>
				
		        <xsl:if test="@ESTADO">
		            <xsl:element name="estado">
		                <xsl:value-of select="@ESTADO" />
		            </xsl:element>
		        </xsl:if>
		        
		      	 <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nm-poliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if> 
                 
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
				
				<xsl:if test="@DSPERSON">
                    <xsl:element name="ds-person">
                        <xsl:value-of select="@DSPERSON" />
                    </xsl:element>
                </xsl:if>
				
		        <xsl:if test="@CDUSUARIO">
		            <xsl:element name="cd-usuario">
		                <xsl:value-of select="@CDUSUARIO" />
		            </xsl:element>
		        </xsl:if>
		        
		        <xsl:if test="@DSUSUARI">
                    <xsl:element name="ds-usuari">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>
                </xsl:if>
                
             
		                          
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>



