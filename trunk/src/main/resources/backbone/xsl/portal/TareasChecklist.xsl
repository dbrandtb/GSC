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
            <item-list xsi:type="java:mx.com.aon.portal.model.TareaChecklistVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDSECCION">
                    <xsl:element name="cd-seccion">
                        <xsl:value-of select="@CDSECCION" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSSECCION">
                    <xsl:element name="ds-seccion">
                        <xsl:value-of select="@DSSECCION" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTAREA">
                    <xsl:element name="cd-tarea">
                        <xsl:value-of select="@CDTAREA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTAREA">
                    <xsl:element name="ds-tarea">
                        <xsl:value-of select="@DSTAREA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTAREA_PADRE">
                    <xsl:element name="cd-tareapadre">
                        <xsl:value-of select="@CDTAREA_PADRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTAREA_PADRE">
                    <xsl:element name="ds-tareapadre">
                        <xsl:value-of select="@DSTAREA_PADRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cd-estado">
                        <xsl:value-of select="@CDESTADO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSESTADO">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSESTADO" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>