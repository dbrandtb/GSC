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
            <item-list xsi:type="java:mx.com.aon.catbo.model.CasoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDPROCESO">
		            <xsl:element name="cd-proceso">
		                <xsl:value-of select="@CDPROCESO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSPROCESO">
		            <xsl:element name="des-proceso">
		                <xsl:value-of select="@DSPROCESO" />
		            </xsl:element>
		        </xsl:if>
                 <xsl:if test="@NMCASO">
                    <xsl:element name="nm-caso">
                        <xsl:value-of select="@NMCASO" />
                    </xsl:element>
                </xsl:if>   
                <xsl:if test="@CDORDENTRABAJO">
                    <xsl:element name="cd-orden-trabajo">
                        <xsl:value-of select="@CDORDENTRABAJO" />
                    </xsl:element>
                </xsl:if>   
                         
              <xsl:if test="@NMVECESCOMPRA">
                    <xsl:element name="nm-veces-compra">
                        <xsl:value-of select="@NMVECESCOMPRA" />
                    </xsl:element>
                </xsl:if>   
                 
                <xsl:if test="@NMCOMPRA_PER">
                    <xsl:element name="nm-veces-compra-per">
                        <xsl:value-of select="@NMCOMPRA_PER" />
                    </xsl:element>
                </xsl:if>   
                         
                <xsl:if test="@NMCOMPRA_CON">
                    <xsl:element name="nm-veces-compra-con">
                        <xsl:value-of select="@NMCOMPRA_CON" />
                    </xsl:element>
                </xsl:if>       
                 <xsl:if test="@NMCOMPRA_DIS">
                    <xsl:element name="nm-veces-compra-dis">
                        <xsl:value-of select="@NMCOMPRA_DIS" />
                    </xsl:element>
                </xsl:if>  
                
                <xsl:if test="@MCANTHASTA">
                    <xsl:element name="nm-cant_hasta">
                        <xsl:value-of select="@MCANTHASTA" />
                    </xsl:element>
                </xsl:if>  
                
                <xsl:if test="@TUNIDAD">
                    <xsl:element name="tunidad">
                        <xsl:value-of select="@TUNIDAD" />
                    </xsl:element>
                </xsl:if>  
              
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

