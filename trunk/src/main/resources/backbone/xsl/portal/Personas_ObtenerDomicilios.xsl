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
            <item-list xsi:type="java:mx.com.aon.portal.model.DomicilioVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMORDDOM">
                    <xsl:element name="num-orden-domicilio">
                        <xsl:value-of select="@NMORDDOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPDOM">
                    <xsl:element name="tipo-domicilio">
                        <xsl:value-of select="@CDTIPDOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPDOM">
                    <xsl:element name="ds-tipo-domicilio">
                        <xsl:value-of select="@DSTIPDOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSDOMICI">
                    <xsl:element name="ds-domicilio">
                        <xsl:value-of select="@DSDOMICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPOSTAL">
                    <xsl:element name="cd-postal">
                        <xsl:value-of select="@CDPOSTAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMNUMERO">
                    <xsl:element name="numero">
                        <xsl:value-of select="@NMNUMERO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMNUMINT">
                    <xsl:element name="numero-interno">
                        <xsl:value-of select="@NMNUMINT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPAIS">
                    <xsl:element name="codigo-pais">
                        <xsl:value-of select="@CDPAIS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPAIS">
                    <xsl:element name="ds-pais">
                        <xsl:value-of select="@DSPAIS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDEDO">
                    <xsl:element name="codigo-estado">
                        <xsl:value-of select="@CDEDO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSEDO">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSEDO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMUNICI">
                    <xsl:element name="codigo-municipio">
                        <xsl:value-of select="@CDMUNICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSMUNICI">
                    <xsl:element name="ds-municipio">
                        <xsl:value-of select="@DSMUNICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCOLONI">
                    <xsl:element name="codigo-colonia">
                        <xsl:value-of select="@CDCOLONI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSCOLONI">
                    <xsl:element name="ds-colonia">
                        <xsl:value-of select="@DSCOLONI" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
