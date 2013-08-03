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
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.ValorDefectoATVCoberturaTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
                <xsl:if test="@CDTIPSIT">
                            <xsl:element name="cdtipsit">
                                <xsl:value-of select="@CDTIPSIT"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDGARANT">
                            <xsl:element name="cdgarant">
                                <xsl:value-of select="@CDGARANT"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@CDATRIBU">
                            <xsl:element name="cdatribu">
                                <xsl:value-of select="@CDATRIBU"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA">
                            <xsl:element name="swobliga">
                                <xsl:value-of select="@SWOBLIGA"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@SWINSERT">
                            <xsl:element name="swinsert">
                                <xsl:value-of select="@SWINSERT"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDEXPRES">
                            <xsl:element name="cdexpres">
                                <xsl:value-of select="@CDEXPRES"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@SWFORMAT">
                            <xsl:element name="swformat">
                                <xsl:value-of select="@SWFORMAT"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@NMLMIN">
                            <xsl:element name="nmlmin">
                                <xsl:value-of select="@NMLMIN"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@NMLMAX">
                            <xsl:element name="nmlmax">
                                <xsl:value-of select="@NMLMAX"/>
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