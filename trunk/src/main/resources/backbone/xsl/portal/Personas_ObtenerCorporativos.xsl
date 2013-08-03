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
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.PersonaCorporativoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDELEMEN">
                    <xsl:element name="cd-elemen">
                        <xsl:value-of select="@CDELEMEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSELEMEN">
                    <xsl:element name="ds-elemen">
                        <xsl:value-of select="@DSELEMEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDGRUPOPER">
                    <xsl:element name="cd-grupo-per">
                        <xsl:value-of select="@CDGRUPOPER" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSGRUPO">
                    <xsl:element name="ds-grupo">
                        <xsl:value-of select="@DSGRUPO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEINICIO">
                    <xsl:element name="fe-inicio">
                        <xsl:value-of select="@FEINICIO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEFIN">
                    <xsl:element name="fe-fin">
                        <xsl:value-of select="@FEFIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                    <xsl:element name="cd-status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSSTATUS">
                    <xsl:element name="ds-status">
                        <xsl:value-of select="@DSSTATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMNOMINA">
                    <xsl:element name="nm-nomina">
                        <xsl:value-of select="@NMNOMINA" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
