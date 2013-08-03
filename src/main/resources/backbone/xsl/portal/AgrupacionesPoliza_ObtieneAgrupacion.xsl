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
            <item-list xsi:type="java:mx.com.aon.portal.model.AgrupacionPolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:if test="@CDGRUPO">
                <xsl:element name="cd-grupo">
                    <xsl:value-of select="@CDGRUPO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDELEMENTO">
                <xsl:element name="cd-elemento">
                    <xsl:value-of select="@CDELEMENTO"/>
                </xsl:element>
            </xsl:if>

            <xsl:if test="@DSELEMEN">
                <xsl:element name="ds-elemen">
                    <xsl:value-of select="@DSELEMEN"/>
                </xsl:element>
            </xsl:if>

            <xsl:if test="@CDUNIECO">
                <xsl:element name="cd-unieco">
                    <xsl:value-of select="@CDUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSUNIECO">
                <xsl:element name="ds-unieco">
                    <xsl:value-of select="@DSUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDTIPRAM">
                <xsl:element name="cd-tipram">
                    <xsl:value-of select="@CDTIPRAM"/>
                </xsl:element>
            </xsl:if>
                <xsl:if test="@DSTIPRAM">
                    <xsl:element name="ds-tipram">
                        <xsl:value-of select="@DSTIPRAM"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@CDRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="ds-ramo">
                        <xsl:value-of select="@DSRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPO">
                    <xsl:element name="cd-tipo">
                        <xsl:value-of select="@CDTIPO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSAGRUPA">
                    <xsl:element name="ds-agrupa">
                        <xsl:value-of select="@DSAGRUPA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cd-estado">
                        <xsl:value-of select="@CDESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSESTADO">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSESTADO"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>