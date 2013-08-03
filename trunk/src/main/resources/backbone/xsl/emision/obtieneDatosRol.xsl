<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

    <xsl:template match="/">
        <master-vO xsi:type="java:mx.com.aon.procesos.emision.model.DatosRolVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </master-vO>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.DatosRolVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMSITUAC">
        			<xsl:element name="nmsituac">
        				<xsl:value-of select="@NMSITUAC"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDROL">
        			<xsl:element name="cdrol">
        				<xsl:value-of select="@CDROL"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cdperson">
                        <xsl:value-of select="@CDPERSON"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWFORMAT">
                    <xsl:element name="swformat">
                        <xsl:value-of select="@SWFORMAT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTABVAL">
                    <xsl:element name="ottabval">
                        <xsl:value-of select="@OTTABVAL"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWPRODUC">
                    <xsl:element name="swproduc">
                        <xsl:value-of select="@SWPRODUC"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWSUPLEM">
                    <xsl:element name="swsuplem">
                        <xsl:value-of select="@SWSUPLEM"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@GB_SWFORMAT">
                    <xsl:element name="gb_swformat">
                        <xsl:value-of select="@GB_SWFORMAT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDATRIBU">
                    <xsl:element name="cdatribu">
                        <xsl:value-of select="@CDATRIBU"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSDESCRIPCION">
                    <xsl:element name="dsdescripcion">
                        <xsl:value-of select="@DSDESCRIPCION"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TIPOOBJETO">
                    <xsl:element name="tipoobjeto">
                        <xsl:value-of select="@TIPOOBJETO"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 