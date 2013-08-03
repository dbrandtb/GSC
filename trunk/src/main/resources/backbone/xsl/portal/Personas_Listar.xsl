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
            <item-list xsi:type="java:mx.com.aon.portal.model.PersonasVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDPERSON">
                    <xsl:element name="codigo-persona">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPPER">
                    <xsl:element name="cd-tip-per">
                        <xsl:value-of select="@CDTIPPER" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="nombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPPER">
                    <xsl:element name="ds-tip-per">
                        <xsl:value-of select="@DSTIPPER" />
                    </xsl:element>
                </xsl:if>
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
                <xsl:if test="@CDRFC">
                    <xsl:element name="cd-rfc">
                        <xsl:value-of select="@CDRFC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMNUMNOM">
                    <xsl:element name="nm-num-nom">
                        <xsl:value-of select="@NMNUMNOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFISJUR">
                    <xsl:element name="ds-fis-jur">
                        <xsl:value-of select="@DSFISJUR" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTFISJUR">
                    <xsl:element name="ot-fis-jur">
                        <xsl:value-of select="@OTFISJUR" />
                    </xsl:element>
                </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
