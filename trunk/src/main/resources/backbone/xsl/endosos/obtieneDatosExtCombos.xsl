<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
    <xsl:template match="/">
        <master-vO xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
        	<xsl:if test="@TIPOOBJETO = 1">
        		<ext-list xsi:type="java:com.biosnet.ice.ext.elements.form.ComboControl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:element name="xtype">combo</xsl:element>      
                    <xsl:element name="width">200</xsl:element>
                    <xsl:element name="empty-text">Seleccione...</xsl:element>                   
                    <xsl:element name="display-field">label</xsl:element>      
                    <xsl:element name="value-field">value</xsl:element> 
                    <xsl:element name="mode">local</xsl:element>                   
                    <xsl:element name="trigger-action">all</xsl:element>      
                    <xsl:element name="type-ahead">true</xsl:element>
                    <xsl:element name="editable">true</xsl:element>
	                
                    <xsl:if test="@DSNOMBRET">
                        <xsl:element name="id">
                            <xsl:value-of select="@DSNOMBRET"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSNOMBRET">
                        <xsl:element name="name">
                            <xsl:value-of select="@DSNOMBRET"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSNOMBRET">
                        <xsl:element name="hidden-name">
                            <xsl:value-of select="@DSNOMBRET"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@VISIBLE">
                        <xsl:if test="@VISIBLE='S'">
                            <xsl:element name="hidden">false</xsl:element>
                        </xsl:if>
                        <xsl:if test="@VISIBLE='N'">
                            <xsl:element name="hidden">true</xsl:element>
                        </xsl:if>
                    </xsl:if> 
                    <xsl:if test="@SWOBLIGA='S'">
                        <xsl:element name="force-selection">true</xsl:element>
                        <xsl:element name="allow-blank">false</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA='N'">
                        <xsl:element name="force-selection">false</xsl:element>
                        <xsl:element name="allow-blank">true</xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSATRIBU">
                        <xsl:element name="field-label">
                            <xsl:value-of select="@DSATRIBU"/>
                        </xsl:element>
                    </xsl:if> 
                    <xsl:if test="@OTTABVAL">
                        <xsl:element name="backup-table">
                            <xsl:value-of select="@OTTABVAL"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSVALOR">
                        <xsl:element name="value">
                            <xsl:value-of select="@DSVALOR"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@OTVALOR">
                        <xsl:element name="hidden-value">
                            <xsl:value-of select="@OTVALOR"/>
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@NMAGRUPA">
                        <xsl:element name="grouping">
                            <xsl:value-of select="@NMAGRUPA"/>
                        </xsl:element>
                    </xsl:if> 
                    <xsl:if test="@NMORDEN">
                        <xsl:element name="grouping-id">
                            <xsl:value-of select="@NMORDEN"/>
                        </xsl:element>
                    </xsl:if>
	            </ext-list>
        	</xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 