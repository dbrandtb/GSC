<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.royalsun.kernel.bo.DatosPolizaBean" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:if test="@CDUNIECO">
                            <xsl:element name="cdunieco">
                                <xsl:value-of select="@CDUNIECO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@CDRAMO">
                            <xsl:element name="cdramo">
                                <xsl:value-of select="@CDRAMO"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@ESTADO">
                            <xsl:element name="estado">
                                <xsl:value-of select="@ESTADO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@NMPOLIZA">
                            <xsl:element name="nmpoliza">
                                <xsl:value-of select="@NMPOLIZA"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@NMSUPLEM">
                            <xsl:element name="nmsuplem">
                                <xsl:value-of select="@NMSUPLEM"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@STATUS">
                            <xsl:element name="status">
                                <xsl:value-of select="@STATUS"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@SWESTADO">
                            <xsl:element name="swestado">
                                <xsl:value-of select="@SWESTADO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@NMSOLICI">
                            <xsl:element name="nmsolici">
                                <xsl:value-of select="@NMSOLICI"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEAUTORI">
                            <xsl:element name="feautori">
                                <xsl:value-of select="@FEAUTORI"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@CDMOTANU">
                            <xsl:element name="cdmotanu">
                                <xsl:value-of select="@CDMOTANU"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEANULAC">
                            <xsl:element name="feanulac">
                                <xsl:value-of select="@FEANULAC"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@SWAUTORI">
                            <xsl:element name="swautori">
                                <xsl:value-of select="@SWAUTORI"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@CDMONEDA">
                            <xsl:element name="cdmoneda">
                                <xsl:value-of select="@CDMONEDA"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEINISUS">
                            <xsl:element name="feinisus">
                                <xsl:value-of select="@FEINISUS"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEFINSUS">
                            <xsl:element name="fefinsus">
                                <xsl:value-of select="@FEFINSUS"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@OTTEMPOT">
                            <xsl:element name="ottempot">
                                <xsl:value-of select="@OTTEMPOT"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEEFECTO">
                            <xsl:element name="feefecto">
                                <xsl:value-of select="@FEEFECTO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@HHEFECTO">
                            <xsl:element name="hhefecto">
                                <xsl:value-of select="@HHEFECTO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEPROREN">
                            <xsl:element name="feproren">
                                <xsl:value-of select="@FEPROREN"/>
                            </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@FEVENCIM">
                            <xsl:element name="fevencim">
                                <xsl:value-of select="@FEVENCIM"/>
                            </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@NMRENOVA">
                            <xsl:element name="nmrenova">
                                <xsl:value-of select="@NMRENOVA"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@FERECIBO">
                            <xsl:element name="ferecibo">
                                <xsl:value-of select="@FERECIBO"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@FEULTSIN">
                            <xsl:element name="feultsin">
                                <xsl:value-of select="@FEULTSIN"/>
                            </xsl:element>                      
                    </xsl:if>
                    <xsl:if test="@NMNUMSIN">
                            <xsl:element name="nmnumsin">
                                <xsl:value-of select="@NMNUMSIN"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDTIPCOA">
                            <xsl:element name="cdtipcoa">
                                <xsl:value-of select="@CDTIPCOA"/>
                            </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@SWTARIFI">
                            <xsl:element name="swtarifa">
                                <xsl:value-of select="@SWTARIFI"/>
                            </xsl:element>                        
                    </xsl:if>
                    
                    
                    <xsl:if test="@SWABRIDO">
                            <xsl:element name="swabrido">
                                <xsl:value-of select="@SWABRIDO"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@FEEMISIO">
                            <xsl:element name="feemisio">
                                <xsl:value-of select="@FEEMISIO"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDPERPAG">
                            <xsl:element name="cdperpag">
                                <xsl:value-of select="@CDPERPAG"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@NMPOLIEX">
                            <xsl:element name="nmpoliex">
                                <xsl:value-of select="@NMPOLIEX"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@NMCUADRO">
                            <xsl:element name="nmcuadro">
                                <xsl:value-of select="@NMCUADRO"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@PORREDAU">
                            <xsl:element name="porredau">
                                <xsl:value-of select="@PORREDAU"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@SWCONSOL">
                            <xsl:element name="swconsol">
                                <xsl:value-of select="@SWCONSOL"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@NMPOLCOI">
                            <xsl:element name="nmpolcoi">
                                <xsl:value-of select="@NMPOLCOI"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@ADPARBEN">
                            <xsl:element name="adparben">
                                <xsl:value-of select="@ADPARBEN"/>
                            </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@NMCERCOI">
                            <xsl:element name="nmcercoi">
                                <xsl:value-of select="@NMCERCOI"/>
                            </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@CDTIPREN">
                            <xsl:element name="cdtipren">
                                <xsl:value-of select="@CDTIPREN"/>
                            </xsl:element>                        
                    </xsl:if>
                 
                    
            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
