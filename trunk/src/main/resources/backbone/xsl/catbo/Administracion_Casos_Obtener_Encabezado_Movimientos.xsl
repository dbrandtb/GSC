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
		        <xsl:if test="@COLORSEMAFORO">
		            <xsl:element name="ind-semaf-color">
		                <xsl:value-of select="@COLORSEMAFORO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDMATRIZ">
		            <xsl:element name="cd-matriz">
		                <xsl:value-of select="@CDMATRIZ" />
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
                <xsl:if test="@CDORDENTRABAJO">
                    <xsl:element name="cd-orden-trabajo">
                        <xsl:value-of select="@CDORDENTRABAJO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cd-formato-orden">
                        <xsl:value-of select="@CDFORMATOORDEN" />
                    </xsl:element>
                </xsl:if>              
                <xsl:if test="@CDMODULO">
                    <xsl:element name="cd-modulo">
                        <xsl:value-of select="@CDMODULO" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@DSMODULO">
                    <xsl:element name="des-modulo">
                        <xsl:value-of select="@DSMODULO" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@DSNIVELAT">
                    <xsl:element name="ds-nivatn">
                        <xsl:value-of select="@DSNIVELAT" />
                    </xsl:element>
                </xsl:if>
                     <xsl:if test="@DSPRIORD">
                    <xsl:element name="des-prioridad">
                        <xsl:value-of select="@DSPRIORD" />
                    </xsl:element>
                </xsl:if>
               <xsl:if test="@CDPRIORD">
                    <xsl:element name="cd-prioridad">
                        <xsl:value-of select="@CDPRIORD" />
                    </xsl:element>
                </xsl:if>
               
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>

