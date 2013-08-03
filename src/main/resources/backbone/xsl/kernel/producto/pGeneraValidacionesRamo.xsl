<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S' "-->
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                <!--/xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose-->
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
            
            <!-- temporal -->
            <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
            
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.ValidacionRamoTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        
                <xsl:if test="@CDFUNCIO">
                            <xsl:element name="cdfuncio">                            
                                <xsl:value-of select="@CDFUNCIO"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDBLOQUE">
                            <xsl:element name="cdbloque">                            
                                <xsl:value-of select="@CDBLOQUE"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@OTSECUEN">
                            <xsl:element name="otsecuen">                            
                                <xsl:value-of select="@OTSECUEN"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDVALIDA">
                            <xsl:element name="cdvalida">                            
                                <xsl:value-of select="@CDVALIDA"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDCONDIC">
                            <xsl:element name="cdcondic">                            
                                <xsl:value-of select="@CDCONDIC"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@DSMSGERR">
                            <xsl:element name="dsmsgerr">                            
                                <xsl:value-of select="@DSMSGERR"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDTIPO">
                            <xsl:element name="cdtipo">                            
                                <xsl:value-of select="@CDTIPO"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDEXPRES">
                            <xsl:element name="cdexpres">                            
                                <xsl:value-of select="@CDEXPRES"/>
                            </xsl:element>                        
                    </xsl:if>
            		<xsl:if test="@cdtipdcv">
                            <xsl:element name="cdtipdcv">                            
                                <xsl:value-of select="@cdtipdcv"/>
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