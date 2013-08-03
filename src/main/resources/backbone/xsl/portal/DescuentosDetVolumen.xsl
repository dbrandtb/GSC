<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.DescuentoDetVolumenVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDDSCTO">
		            <xsl:element name="cd-dscto">
		                <xsl:value-of select="@CDDSCTO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDDSCTOD">
		            <xsl:element name="cd-dsctod">
		                <xsl:value-of select="@CDDSCTOD" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@MNVOLINI">
		            <xsl:element name="mn-vol-ini">
		                <xsl:value-of select="@MNVOLINI" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@MNVOLFIN">
		            <xsl:element name="mn-vol-fin">
		                <xsl:value-of select="@MNVOLFIN" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@PRDESCTO">
		            <xsl:element name="pr-descto">
		                <xsl:value-of select="@PRDESCTO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@MNDESCTO">
		            <xsl:element name="mn-descto">
		                <xsl:value-of select="@MNDESCTO" />
		            </xsl:element>
		        </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>