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
		       <xsl:if test="@DSPROCESO">
                    <xsl:element name="des-proceso">
                        <xsl:value-of select="@DSPROCESO" />
                    </xsl:element>
                </xsl:if>   
                <xsl:if test="@CDPRIORD">
                    <xsl:element name="cd-prioridad">
                        <xsl:value-of select="@CDPRIORD" />
                    </xsl:element>
                </xsl:if>    
                  <xsl:if test="@DSPRIORD">
                    <xsl:element name="des-prioridad">
                        <xsl:value-of select="@DSPRIORD" />
                    </xsl:element>
                </xsl:if>   
                
                <xsl:if test="@DSNIVATN">
		            <xsl:element name="ds-nivatn">
		                <xsl:value-of select="@DSNIVATN" />
		            </xsl:element>
		        </xsl:if>
                
                <xsl:if test="@CDNIVATN">
		            <xsl:element name="cd-nivatn">
		                <xsl:value-of select="@CDNIVATN" />
		            </xsl:element>
		        </xsl:if>

		        <xsl:if test="@NMCASO">
		            <xsl:element name="nm-caso">
		                <xsl:value-of select="@NMCASO" />
		            </xsl:element>
		        </xsl:if>
		       <xsl:if test="@DSUNIECO">
                    <xsl:element name="des-unieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if> 
                 
                
                     <xsl:if test="@IND_SEMAF_COLOR">
                    <xsl:element name="ind-semaf-color">
                        <xsl:value-of select="@IND_SEMAF_COLOR" />
                    </xsl:element>
                </xsl:if>                            
                 
		        
		        <xsl:if test="@CDORDENTRABAJO">
		            <xsl:element name="cd-orden-trabajo">
		                <xsl:value-of select="@CDORDENTRABAJO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDMODULO">
                    <xsl:element name="cd-modulo">
                        <xsl:value-of select="@CDMODULO" />
                    </xsl:element>
                </xsl:if> 
		           
              <xsl:if test="@CDSTATUS">
                    <xsl:element name="cd-status">
                        <xsl:value-of select="@CDSTATUS" />
                    </xsl:element>
                </xsl:if>   
                  
           <xsl:if test="@DSSTATUS">
                    <xsl:element name="des-status">
                        <xsl:value-of select="@DSSTATUS" />
                    </xsl:element>
                </xsl:if>  
                
            <xsl:if test="@TRESOLUCION">
                    <xsl:element name="tre-solucion">
                        <xsl:value-of select="@TRESOLUCION" />
                    </xsl:element>
                </xsl:if>       
              <xsl:if test="@TRESUNIDAD">
                    <xsl:element name="tres-unidad">
                        <xsl:value-of select="@TRESUNIDAD" />
                    </xsl:element>
                </xsl:if> 
                                        
                 
            <xsl:if test="@NMCOMPRA">
                    <xsl:element name="nm-compra">
                        <xsl:value-of select="@NMCOMPRA" />
                    </xsl:element>
                </xsl:if> 
               <xsl:if test="@TUNIDAD">
                    <xsl:element name="tunidad">
                        <xsl:value-of select="@TUNIDAD" />
                    </xsl:element>
                </xsl:if>    
              <xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cd-formato-orden">
                        <xsl:value-of select="@CDFORMATOORDEN" />
                    </xsl:element>
                </xsl:if>       
         
			 <xsl:if test="@DSUSUARI">
                    <xsl:element name="des-usuario">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>
                </xsl:if>  
           <xsl:if test="@DSROLMAT">
                    <xsl:element name="des-rolmat">
                        <xsl:value-of select="@DSROLMAT" />
                    </xsl:element>
                </xsl:if>  
             <xsl:if test="@NMOVIMIENTO">
                    <xsl:element name="n-movimiento">
                        <xsl:value-of select="@NMOVIMIENTO" />
                    </xsl:element>
                </xsl:if>          
              <xsl:if test="@NMARCHIVO">
                    <xsl:element name="nm-archivo">
                        <xsl:value-of select="@NMARCHIVO" />
                    </xsl:element>
                </xsl:if>  
              
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

