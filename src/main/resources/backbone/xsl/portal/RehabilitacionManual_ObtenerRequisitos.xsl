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
            <item-list xsi:type="java:mx.com.aon.portal.model.RehabilitacionManual_RequisitosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDELEMENTO">
                    <xsl:element name="cd-elemento">
                        <xsl:value-of select="@CDELEMENTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDDOCUMEN">
                    <xsl:element name="cd-documen">
                        <xsl:value-of select="@CDDOCUMEN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDDOCXCTA">
                    <xsl:element name="cd-doc-x-cta">
                        <xsl:value-of select="@CDDOCXCTA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSFORMATO">
                    <xsl:element name="ds-formato">
                        <xsl:value-of select="@DSFORMATO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSREQUISITO">
                    <xsl:element name="ds-requisito">
                        <xsl:value-of select="@DSREQUISITO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@BLOBARCHIVO">
                    <xsl:element name="blob-archivo">
                        <xsl:value-of select="@BLOBARCHIVO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@INDENTREGA">
                    <xsl:element name="ind-entrega">
                        <xsl:value-of select="@INDENTREGA" />
                    </xsl:element>
                </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
