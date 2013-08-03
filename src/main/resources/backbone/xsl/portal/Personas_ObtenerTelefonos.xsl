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
            <item-list xsi:type="java:mx.com.aon.portal.model.TelefonoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMORDTEL">
                    <xsl:element name="numero-orden">
                        <xsl:value-of select="@NMORDTEL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPTEL">
                    <xsl:element name="cod-tip-tel">
                        <xsl:value-of select="@CDTIPTEL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPTEL">
                    <xsl:element name="ds-tip-tel">
                        <xsl:value-of select="@DSTIPTEL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDAREA">
                    <xsl:element name="cod-area">
                        <xsl:value-of select="@CDAREA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMTELEFO">
                    <xsl:element name="num-telef">
                        <xsl:value-of select="@NMTELEFO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMEXTENS">
                    <xsl:element name="num-extens">
                        <xsl:value-of select="@NMEXTENS" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
