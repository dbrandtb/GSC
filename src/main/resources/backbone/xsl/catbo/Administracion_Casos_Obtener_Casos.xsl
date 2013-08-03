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
		        <xsl:if test="@CDMATRIZ">
		            <xsl:element name="cd-matriz">
		                <xsl:value-of select="@CDMATRIZ" />
		            </xsl:element>
		        </xsl:if>		        
		        <xsl:if test="@NMCASO">
		            <xsl:element name="nm-caso">
		                <xsl:value-of select="@NMCASO" />
		            </xsl:element>
		        </xsl:if>
                 <xsl:if test="@CDPROCESO">
                    <xsl:element name="cd-proceso">
                        <xsl:value-of select="@CDPROCESO" />
                    </xsl:element>
                </xsl:if>   
               <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>  
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="des-unieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>                  
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="ds-ramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>  		       
		        <xsl:if test="@CDORDENTRABAJO">
		            <xsl:element name="cd-orden-trabajo">
		                <xsl:value-of select="@CDORDENTRABAJO" />
		            </xsl:element>
		        </xsl:if>       
                <xsl:if test="@DSPROCESO">
                    <xsl:element name="des-proceso">
                        <xsl:value-of select="@DSPROCESO" />
                    </xsl:element>
                </xsl:if>   
                <xsl:if test="@CDUSUARIO">
                    <xsl:element name="cd-usuario">
                        <xsl:value-of select="@CDUSUARIO" />
                    </xsl:element>
                </xsl:if>   
                <xsl:if test="@DSUSUARI">
                    <xsl:element name="des-usuario">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>
                </xsl:if>  
              <xsl:if test="@CDPRIORD">
                    <xsl:element name="cd-prioridad">
                        <xsl:value-of select="@CDPRIORD" />
                    </xsl:element>
                </xsl:if>    
              
                <xsl:if test="@DSPRIORIDAD">
                    <xsl:element name="des-prioridad">
                        <xsl:value-of select="@DSPRIORIDAD" />
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
                 
            <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>       
          <xsl:if test="@DSNOMBRE">
                    <xsl:element name="des-nombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>   
        <xsl:if test="@IND_SEMAF_COLOR">
                    <xsl:element name="ind-semaf-color">
                        <xsl:value-of select="@IND_SEMAF_COLOR" />
                    </xsl:element>
                </xsl:if>  
        <xsl:if test="@IDSMETCONTAC">
                    <xsl:element name="id-met-contact">
                        <xsl:value-of select="@IDSMETCONTAC" />
                    </xsl:element>
                </xsl:if>                           
          <xsl:if test="@FEREGISTRO">
                    <xsl:element name="fe-registro">
                        <xsl:value-of select="@FEREGISTRO" />
                    </xsl:element>
                </xsl:if>  
          <xsl:if test="@FERESOLUCION">
                    <xsl:element name="fe-resolucion">
                        <xsl:value-of select="@FERESOLUCION" />
                    </xsl:element>
                </xsl:if>        
         <xsl:if test="@TRESOLUCION">
                    <xsl:element name="tre-solucion">
                        <xsl:value-of select="@TRESOLUCION" />
                    </xsl:element>
                </xsl:if>                   
                           
            <xsl:if test="@FEESCALAMIENTO">
                    <xsl:element name="fe-escalamiento">
                        <xsl:value-of select="@FEESCALAMIENTO" />
                    </xsl:element>
                </xsl:if>  
       <xsl:if test="@NMVECESCOMPRA">
                    <xsl:element name="nm-veces-compra">
                        <xsl:value-of select="@NMVECESCOMPRA" />
                    </xsl:element>
                </xsl:if>           
         <xsl:if test="@TESCALAMIENTO">
                    <xsl:element name="tes-calamiento">
                        <xsl:value-of select="@TESCALAMIENTO" />
                    </xsl:element>
                </xsl:if>  
           
          <xsl:if test="@IND_POLIZA">
                    <xsl:element name="ind-poliza">
                        <xsl:value-of select="@IND_POLIZA" />
                    </xsl:element>
                </xsl:if>          
                 <xsl:if test="@CDNUMERORDENCIA">
                    <xsl:element name="cdnumerordencia">
                        <xsl:value-of select="@CDNUMERORDENCIA" />
                    </xsl:element>
                </xsl:if>                
				<xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cd-formato-orden">
                        <xsl:value-of select="@CDFORMATOORDEN" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@NMCOMPRA">
                    <xsl:element name="nm-compra">
                        <xsl:value-of select="@NMCOMPRA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSMODULO">
                    <xsl:element name="des-modulo">
                        <xsl:value-of select="@DSMODULO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDNIVATN">
                    <xsl:element name="cd-nivatn">
                        <xsl:value-of select="@CDNIVATN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNIVATN">
                    <xsl:element name="ds-nivatn">
                        <xsl:value-of select="@DSNIVATN" />
                    </xsl:element>
                </xsl:if>
              	<xsl:if test="@PORCENTAJE">
                    <xsl:element name="porcentaje">
                        <xsl:value-of select="@PORCENTAJE" />
                    </xsl:element>
                </xsl:if>
              
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

