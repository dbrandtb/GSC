<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'"-->
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
            <cursor xsi:type="java:mx.com.royalsun.kernel.bo.AtributosTarificacionBean" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:if test="@NMSITUAC">                        
                            <xsl:element name="nm-situac">
                                <xsl:value-of select="@NMSITUAC"/>
                            </xsl:element>                        
                    </xsl:if>
                    <xsl:if test="@CDGARANT">                        
                            <xsl:element name="cd-garant">
                                <xsl:value-of select="@CDGARANT"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSGARANT">                        
                            <xsl:element name="ds-garant">
                                <xsl:value-of select="@DSGARANT"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@CDCONTAR">
                            <xsl:element name="cd-contar">
                                <xsl:value-of select="@CDCONTAR"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSCONTAR">
                            <xsl:element name="ds-contar">
                                <xsl:value-of select="@DSCONTAR"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@CDTIPCON">
                            <xsl:element name="cd-tipcon">
                                <xsl:value-of select="@CDTIPCON"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@POCOMPRO">
                            <xsl:element name="pro-compro">
                                <xsl:value-of select="@POCOMPRO"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@NMIMPORT">
                            <xsl:element name="nm-import">
                                <xsl:value-of select="@NMIMPORT"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@PTIMPORT">
                            <xsl:element name="pt-import">
                                <xsl:value-of select="@PTIMPORT"/>
                            </xsl:element>
                    </xsl:if>
                    <xsl:if test="@PTIMPREC">
                            <xsl:element name="pt-imprec">
                                <xsl:value-of select="@PTIMPREC"/>
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
