<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="row">
        <atributo-vO xsi:type="java:mx.com.aon.portal.model.reporte.AtributosVO">
		<xsl:if test="@CDPLANTI">
			<xsl:element name="cd-plantilla">
				<xsl:value-of select="@CDPLANTI"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDATRIBU">
			<xsl:element name="cd-atributo">
				<xsl:value-of select="@CDATRIBU"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSATRIBU">
			<xsl:element name="ds-atributo">
				<xsl:value-of select="@DSATRIBU"/>
			</xsl:element>
		</xsl:if>
        <xsl:if test="@SWFORMAT">
			<xsl:element name="sw-format">
				<xsl:value-of select="@SWFORMAT"/>
			</xsl:element>
		</xsl:if>
        <xsl:if test="@NMLMIN">
			<xsl:element name="nm-lmin">
				<xsl:value-of select="@NMLMIN"/>
			</xsl:element>
		</xsl:if>
        <xsl:if test="@NMLMAX">
			<xsl:element name="nm-lmax">
				<xsl:value-of select="@NMLMAX"/>
			</xsl:element>
        </xsl:if>
        <xsl:if test="@OTTABVAL">
			<xsl:element name="ot-tabval">
				<xsl:value-of select="@OTTABVAL"/>
			</xsl:element>
		</xsl:if>
        <xsl:if test="@CDTABLA">
			<xsl:element name="cdot-tabval">
				<xsl:value-of select="@CDTABLA"/>
			</xsl:element>
		</xsl:if>
        </atributo-vO>
    </xsl:template>
</xsl:stylesheet>   