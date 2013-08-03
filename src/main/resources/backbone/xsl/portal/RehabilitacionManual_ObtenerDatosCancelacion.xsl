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
            <item-list xsi:type="java:mx.com.aon.portal.model.RehabilitacionManual_PolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@INCISO">
                    <xsl:element name="nm-inciso">
                        <xsl:value-of select="@INCISO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nm-suplem">
                        <xsl:value-of select="@NMSUPLEM"/>
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FECANCEL">
                    <xsl:element name="fecha-cancelacion">
                        <xsl:value-of select="@FECANCEL" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDRAZON">
                    <xsl:element name="cd-razon-cancelacion">
                        <xsl:value-of select="@CDRAZON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSRAZON">
                    <xsl:element name="ds-razon-cancelacion">
                        <xsl:value-of select="@DSRAZON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@COMENTARIOS">
                    <xsl:element name="comentarios-cancelacion">
                        <xsl:value-of select="@COMENTARIOS" />
                    </xsl:element>
                </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
