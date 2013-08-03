<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

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
            <item-list xsi:type="java:mx.com.aon.portal.model.CarritoComprasRolesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            
            <xsl:if test="@CDBANCO">
                <xsl:element name="cd-banco">
                    <xsl:value-of select="@CDBANCO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSNOMBRE">
                <xsl:element name="ds-nombre">
                    <xsl:value-of select="@DSNOMBRE"/>
                </xsl:element>
            </xsl:if>

            <xsl:if test="@CDPERSON">
                <xsl:element name="cd-person">
                    <xsl:value-of select="@CDPERSON"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@CDFORPAG">
                <xsl:element name="cd-for-pag">
                    <xsl:value-of select="@CDFORPAG"/>
                </xsl:element>
            </xsl:if>
            
            
            <xsl:if test="@DSFORPAG">
                <xsl:element name="ds-for-pag">
                    <xsl:value-of select="@DSFORPAG"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@CDTITARG">
                <xsl:element name="cd-tit-arg">
                    <xsl:value-of select="@CDTITARG"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSTITARJ">
                <xsl:element name="ds-tit-arg">
                    <xsl:value-of select="@DSTITARJ"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@NMTARJ">
                <xsl:element name="nm-tarj">
                    <xsl:value-of select="@NMTARJ"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@FEVENCE">
                <xsl:element name="fe-vence">
                    <xsl:value-of select="@FEVENCE"/>
                </xsl:element>
            </xsl:if>
            
            
             </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>