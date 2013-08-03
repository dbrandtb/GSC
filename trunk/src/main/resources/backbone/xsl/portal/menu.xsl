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
        <menu-principal-vO xsi:type="java:mx.com.aon.portal.model.MenuPrincipalVO">		
		<xsl:if test="@DSMENU_EST">
			<xsl:element name="text">
				<xsl:value-of select="@DSMENU_EST"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDRAMO">
			<xsl:element name="clave-ramo">
				<xsl:value-of select="@CDRAMO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="clave-tipo-situacion">
				<xsl:value-of select="@CDTIPSIT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSURL">
			<xsl:element name="href">
				<xsl:value-of select="@DSURL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDNIVEL">
			<xsl:element name="nivel">
				<xsl:value-of select="@CDNIVEL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDNIVEL_PADRE">
			<xsl:element name="nivel-padre">
				<xsl:value-of select="@CDNIVEL_PADRE"/>
			</xsl:element>
		</xsl:if>		
        </menu-principal-vO>
    </xsl:template>
</xsl:stylesheet>        