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
            <item-list xsi:type="java:mx.com.aon.portal.model.RehabilitacionManual_PolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:if test="@ASEGURADO">
                <xsl:element name="ds-asegurado">
                    <xsl:value-of select="@ASEGURADO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDUNIECO">
                <xsl:element name="cd-aseguradora">
                    <xsl:value-of select="@CDUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSUNIECO">
                <xsl:element name="ds-aseguradora">
                    <xsl:value-of select="@DSUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDRAMO">
                <xsl:element name="cd-producto">
                    <xsl:value-of select="@CDRAMO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSRAMO">
                <xsl:element name="ds-producto">
                    <xsl:value-of select="@DSRAMO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@NMPOLIZA">
                <xsl:element name="nm-poliza">
                    <xsl:value-of select="@NMPOLIZA"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@FEEFECTO">
                <xsl:element name="fe-efecto">
                    <xsl:value-of select="@FEEFECTO"/>
                </xsl:element>
            </xsl:if> 
            <xsl:if test="@FEVENCIM">
                <xsl:element name="fecha-vencimiento">
                    <xsl:value-of select="@FEVENCIM"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDUNIAGE">
                <xsl:element name="cd-uni-age">
                    <xsl:value-of select="@CDUNIAGE"/>
                </xsl:element>
            </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>