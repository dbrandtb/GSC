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
            <cursor xsi:type="java:mx.com.royalsun.newalea.xml.cacheProducto.jaxb.impl.CompCoberturasTypeImpl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
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
                    <xsl:if test="@CDTIPCAP">
                            <xsl:element name="cdtipcap">
                                <xsl:value-of select="@CDTIPCAP"/>
                             </xsl:element>
                        
                    </xsl:if>
                    <xsl:if test="@CDCAPITA">
                            <xsl:element name="cdcapita">
                                <xsl:value-of select="@CDCAPITA"/>
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
                    <xsl:if test="@CDCONDIC">
                            <xsl:element name="cdcondic">
                                <xsl:value-of select="@CDCONDIC"/>
                             </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@SWMANUAL">
                            <xsl:element name="swmanual">
                                <xsl:value-of select="@SWMANUAL"/>
                             </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@cdtipbca">
                            <xsl:element name="cdtipbca">
                                <xsl:value-of select="@cdtipbca"/>
                             </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@ptvalbas">
                            <xsl:element name="ptvalbas">
                                <xsl:value-of select="@ptvalbas"/>
                             </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@pttasa">
                            <xsl:element name="pttasa">
                                <xsl:value-of select="@pttasa"/>
                             </xsl:element>                        
                    </xsl:if>
					<xsl:if test="@cdtipfra">
                            <xsl:element name="cdtipfra">
                                <xsl:value-of select="@cdtipfra"/>
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