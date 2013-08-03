<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
    <xsl:template match="/">
        <master-vO xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <ext-list xsi:type="java:com.biosnet.ice.ext.elements.form.TextFieldControl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
                <xsl:element name="xtype">textfield</xsl:element>
                <xsl:element name="hidde-parent">true</xsl:element>                            
                <xsl:element name="width">200</xsl:element>
                <xsl:element name="disabled">true</xsl:element>
                <xsl:element name="label-separator">:</xsl:element>
                
	                <xsl:if test="@SWOBLIGA='S'">
                        <xsl:element name="allow-blank">false</xsl:element>
	                    <!--
                        <xsl:element name="allow-blank">
	                        <xsl:value-of select="@SWOBLIGA"/>
	                    </xsl:element>
                        -->
	                </xsl:if>
                    <xsl:if test="@SWOBLIGA='N'">
                        <xsl:element name="allow-blank">true</xsl:element>
                        <!--  
                        <xsl:element name="allow-blank">
                            <xsl:value-of select="@SWOBLIGA"/>
                        </xsl:element>
                        -->
                    </xsl:if>
                <xsl:if test="@NMLMIN">
                    <xsl:element name="min-length"><xsl:value-of select="@NMLMIN" /></xsl:element>
                    <xsl:element name="min-length-text">El mínimo de caracteres es <xsl:value-of select="@NMLMIN" /></xsl:element>
                </xsl:if>
                <xsl:if test="@NMLMAX">
                    <xsl:element name="max-length"><xsl:value-of select="@NMLMAX" /></xsl:element>
                    <xsl:element name="max-length-text">El máximo de caracteres es <xsl:value-of select="@NMLMAX" /></xsl:element>
                </xsl:if>
                <xsl:if test="@DSATRIBU">
                    <xsl:element name="field-label">
                        <xsl:value-of select="@DSATRIBU"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="id">
                        <xsl:value-of select="@DSNOMBRE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="name">
                        <xsl:value-of select="@DSNOMBRE"/>
                    </xsl:element>
                </xsl:if>

                <xsl:choose>                    
                <xsl:when test="@DSVALOR = ''">
                    <xsl:if test="@OTVALOR">
                        <xsl:element name="value">
                            <xsl:value-of select="@OTVALOR"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:when>
                <xsl:when test="@DSVALOR = NULL">
                    <xsl:if test="@OTVALOR">
                        <xsl:element name="value">
                            <xsl:value-of select="@OTVALOR"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="@DSVALOR">
                        <xsl:element name="value">
                            <xsl:value-of select="@DSVALOR"/>
                        </xsl:element>
                    </xsl:if>
                </xsl:otherwise>                    
                </xsl:choose>

            </ext-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 