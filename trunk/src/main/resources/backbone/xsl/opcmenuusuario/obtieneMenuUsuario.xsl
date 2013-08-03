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
        <menu-vO xsi:type="java:mx.com.aon.portal.model.menuusuario.MenuVO">
        <xsl:if test="@CDMENU">
            <xsl:element name="cd-menu">
                <xsl:value-of select="@CDMENU"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@DSMENU">
            <xsl:element name="ds-menu">
                <xsl:value-of select="@DSMENU"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDELEMENTO">
            <xsl:element name="cd-elemento">
                <xsl:value-of select="@CDELEMENTO"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@DSELEMEN">
            <xsl:element name="ds-elemento">
                <xsl:value-of select="@DSELEMEN"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDPERSON">
            <xsl:element name="cd-person">
                <xsl:value-of select="@CDPERSON"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@DSNOMBRE">
            <xsl:element name="ds-person">
                <xsl:value-of select="@DSNOMBRE"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDROL">
            <xsl:element name="cd-rol">
                <xsl:value-of select="@CDROL"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDUSUARIO">
            <xsl:element name="cd-usuario">
                <xsl:value-of select="@CDUSUARIO"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@DSUSUARI">
            <xsl:element name="ds-usuario">
                <xsl:value-of select="@DSUSUARI"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDESTADO">
            <xsl:element name="cd-estado">
                <xsl:value-of select="@CDESTADO"/>
            </xsl:element>
        </xsl:if>
        <xsl:if test="@CDTIPOMENU">
                <xsl:element name="cd-tipo-menu">
                    <xsl:value-of select="@CDTIPOMENU"/>
                </xsl:element>
        </xsl:if>
      </menu-vO>
    </xsl:template>
</xsl:stylesheet>       