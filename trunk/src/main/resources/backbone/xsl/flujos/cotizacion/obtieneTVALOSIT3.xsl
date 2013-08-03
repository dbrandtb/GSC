<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.DatosRolVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@NMSITUAC">
                    <xsl:element name="nmsituac">
                        <xsl:value-of select="@NMSITUAC" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWFORMAT">
                    <xsl:element name="swformat">
                        <xsl:value-of select="@SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWOBLIGA">
                    <xsl:element name="swobliga">
                        <xsl:value-of select="@SWOBLIGA" />
                    </xsl:element>
                </xsl:if>
                
              	<xsl:if test="@NMLMAX">
                    <xsl:element name="nmlmax">
                        <xsl:value-of select="@NMLMAX" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMLMIN">
                    <xsl:element name="nmlmin">
                        <xsl:value-of select="@NMLMIN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@OTTABVAL">
                    <xsl:element name="ottabval">
                        <xsl:value-of select="@OTTABVAL" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWPRODUC">
                    <xsl:element name="swproduc">
                        <xsl:value-of select="@SWPRODUC" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWSUPLEM">
                    <xsl:element name="swsuplem">
                        <xsl:value-of select="@SWSUPLEM" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@GB_SWFORMAT">
                    <xsl:element name="gb_swformat">
                        <xsl:value-of select="@GB_SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDATRIBU">
                    <xsl:element name="cdatribu">
                        <xsl:value-of select="@CDATRIBU" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSATRIBU">
                    <xsl:element name="dsatribu">
                        <xsl:value-of select="@DSATRIBU" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@OTVALOR">
                    <xsl:element name="otvalor">
                        <xsl:value-of select="@OTVALOR" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="dsnombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSDESCRIPCION">
                    <xsl:element name="dsdescripcion">
                        <xsl:value-of select="@DSDESCRIPCION" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TIPOOBJETO">
                    <xsl:element name="tipoobjeto">
                        <xsl:value-of select="@TIPOOBJETO" />
                    </xsl:element>
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
