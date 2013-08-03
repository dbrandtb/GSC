<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDPROCXCTA">
                    <xsl:element name="cdprocxcta">
                        <xsl:value-of select="@CDPROCXCTA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPROCESO">
                    <xsl:element name="cdproceso">
                        <xsl:value-of select="@CDPROCESO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cdunieco">
                        <xsl:value-of select="@CDUNIECO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cdperson">
                        <xsl:value-of select="@CDPERSON"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cdestado">
                        <xsl:value-of select="@CDESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDELEMENTO">
                    <xsl:element name="cdelemento">
                        <xsl:value-of select="@CDELEMENTO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPROCESO">
                    <xsl:element name="dsproceso">
                        <xsl:value-of select="@DSPROCESO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPROCESOFLUJO">
                    <xsl:element name="dsprocesoflujo">
                        <xsl:value-of select="@DSPROCESOFLUJO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSELEMEN">
                    <xsl:element name="dselemen">
                        <xsl:value-of select="@DSELEMEN"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="dsramo">
                        <xsl:value-of select="@DSRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="dsunieco">
                        <xsl:value-of select="@DSUNIECO"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>        