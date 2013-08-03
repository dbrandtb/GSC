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
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.IncisosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
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
                <!--  
                <xsl:if test="@OTVALOR01">
                    <xsl:element name="">
                        <xsl:value-of select="@OTVALOR01"/>
                    </xsl:element>
                </xsl:if>
                -->
                <xsl:if test="@SUMASEG">
                    <xsl:element name="ds-suma-asegurada">
                        <xsl:value-of select="@SUMASEG"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DEDUCIBLE">
                    <xsl:element name="ds-deducible">
                        <xsl:value-of select="@DEDUCIBLE"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 