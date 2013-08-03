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
            <item-list xsi:type="java:mx.com.aon.portal.model.configworkflow.WorkFlowPs2VO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDPROCXCTA">
                    <xsl:element name="cdprocxcta">
                        <xsl:value-of select="@CDPROCXCTA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPASO">
                    <xsl:element name="cdpaso">
                        <xsl:value-of select="@CDPASO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cdestado">
                        <xsl:value-of select="@CDESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPASOEXITO">
                    <xsl:element name="cdpasoexito">
                        <xsl:value-of select="@CDPASOEXITO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPASOFRACASO">
                    <xsl:element name="cdpasofracaso">
                        <xsl:value-of select="@CDPASOFRACASO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCONDICI">
                    <xsl:element name="cdcondici">
                        <xsl:value-of select="@CDCONDICI"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTITULO">
                    <xsl:element name="cdtitulo">
                        <xsl:value-of select="@CDTITULO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMORDEN">
                    <xsl:element name="nmorden">
                        <xsl:value-of select="@NMORDEN"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPASO">
                    <xsl:element name="dspaso">
                        <xsl:value-of select="@DSPASO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPASOEXITO">
                    <xsl:element name="dspasoexito">
                        <xsl:value-of select="@DSPASOEXITO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPASOFRACASO">
                    <xsl:element name="dspasofracaso">
                        <xsl:value-of select="@DSPASOFRACASO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTITULO">
                    <xsl:element name="dstitulo">
                        <xsl:value-of select="@DSTITULO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWFINAL">
                    <xsl:element name="swfinal">
                        <xsl:value-of select="@SWFINAL"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>        