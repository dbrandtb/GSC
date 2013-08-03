<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:if test="@TIPOOBJETO ='1'">
                <item-lista xsi:type="java:com.biosnet.ice.ext.elements.form.ComboControl">
                    <xsl:element name="xtype">combo</xsl:element>
                    <xsl:element name="display-field">label</xsl:element>
                    <xsl:element name="empty-text">Seleccione...</xsl:element>
                    <xsl:element name="trigger-action">all</xsl:element>
                    <xsl:element name="type-ahead">true</xsl:element>
                    <xsl:element name="editable">true</xsl:element>
                    <xsl:element name="mode">local</xsl:element>
                    <xsl:element name="force-selection">true</xsl:element>
                    <xsl:element name="list-width">200</xsl:element>
                    <xsl:element name="width">200</xsl:element>
                    <xsl:element name="select-on-focus">true</xsl:element>
                    <xsl:if test="@DSNOMBRET">
                        <xsl:element name="name">
                            <xsl:value-of select="@DSNOMBRET" />
                        </xsl:element>
                        <xsl:element name="id">
                            <xsl:value-of select="@DSNOMBRET" />
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA='S'">
                        <xsl:element name="force-selection">true</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA='N'">
                        <xsl:element name="force-selection">false</xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSATRIBU">
                        <xsl:element name="field-label">
                            <xsl:value-of select="@DSATRIBU" />
                        </xsl:element>
                    </xsl:if>
                </item-lista>
            </xsl:if>

            <xsl:if test="@TIPOOBJETO ='2'">

                <item-lista xsi:type="java:com.biosnet.ice.ext.elements.form.TextFieldControl">
                    <xsl:if test="@SWFORMAT='A'">
                        <xsl:element name="xtype">textfield</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWFORMAT='N'">
                        <xsl:element name="xtype">numberfield</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWFORMAT='P'">
                        <xsl:element name="xtype">numberfield</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWFORMAT='F'">
                        <xsl:element name="xtype">textfield</xsl:element>
                    </xsl:if>
                    <xsl:element name="hidde-parent">true</xsl:element>
                    <xsl:element name="width">200</xsl:element>
                    <xsl:if test="@SWOBLIGA='S'">
                        <xsl:element name="allow-blank">false</xsl:element>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA='N'">
                        <xsl:element name="allow-blank">true</xsl:element>
                    </xsl:if>

                    <xsl:if test="@DSNOMBRET">
                        <xsl:element name="name">
                            <xsl:value-of select="@DSNOMBRET" />
                        </xsl:element>
                        <xsl:element name="id">
                            <xsl:value-of select="@DSNOMBRET" />
                        </xsl:element>
                    </xsl:if>

                    <xsl:if test="@NMLMIN">
                        <xsl:element name="min-length">
                            <xsl:value-of select="@NMLMIN" />
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@NMLMAX">
                        <xsl:element name="max-length">
                            <xsl:value-of select="@NMLMAX" />
                        </xsl:element>
                    </xsl:if>
                    <xsl:if test="@DSATRIBU">
                        <xsl:element name="field-label">
                            <xsl:value-of select="@DSATRIBU" />
                        </xsl:element>
                    </xsl:if>

                </item-lista>
            </xsl:if>

        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
