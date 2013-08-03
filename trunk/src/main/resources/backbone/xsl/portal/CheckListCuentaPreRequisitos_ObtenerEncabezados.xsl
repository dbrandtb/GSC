<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java"
                exclude-result-prefixes="java">


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
            <item-list xsi:type="java:mx.com.aon.portal.model.EncabezadosCheckListCuentaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <!-- Aqui ponemos nuestro VO -->
            <xsl:if test="@CDELEMENTO">
                <xsl:element name="cd-elemen">
                    <xsl:value-of select="@CDELEMENTO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSELEMEN">
                <xsl:element name="ds-nombre">
                    <xsl:value-of select="@DSELEMEN"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDCONFIG">
                <xsl:element name="cd-config">
                    <xsl:value-of select="@CDCONFIG"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSCONFIG">
                <xsl:element name="ds-config">
                    <xsl:value-of select="@DSCONFIG"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDLINOPE">
                <xsl:element name="cd-lin-ope">
                    <xsl:value-of select="@CDLINOPE"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSLINOPE">
                <xsl:element name="ds-lin-ope">
                    <xsl:value-of select="@DSLINOPE"/>
                </xsl:element>
            </xsl:if>
           </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>