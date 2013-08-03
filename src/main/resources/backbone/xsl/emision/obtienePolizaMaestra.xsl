<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </wrapper-resultados>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.PolizaMaestraVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDPOLMTRA">
                    <xsl:element name="cdpolmtra">
                        <xsl:value-of select="@CDPOLMTRA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDELEMENTO">
                    <xsl:element name="cdelemento">
                        <xsl:value-of select="@CDELEMENTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCIA">
                    <xsl:element name="cdcia">
                        <xsl:value-of select="@CDCIA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPO">
                    <xsl:element name="cdtipo">
                        <xsl:value-of select="@CDTIPO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIEX">
                    <xsl:element name="nmpoliex">
                        <xsl:value-of select="@NMPOLIEX" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nmpoliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEINICIO">
                    <xsl:element name="feinicio">
                        <xsl:value-of select="@FEINICIO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEFIN">
                    <xsl:element name="fefin">
                        <xsl:value-of select="@FEFIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSELEMEN">
                    <xsl:element name="dselemen">
                        <xsl:value-of select="@DSELEMEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="dsramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="dsunieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPO">
                    <xsl:element name="dstipo">
                        <xsl:value-of select="@DSTIPO" />
                    </xsl:element>
                  </xsl:if>
                   <xsl:if test="@CDNUMPOL">
                    <xsl:element name="cdnumpol">
                        <xsl:value-of select="@CDNUMPOL" />
                    </xsl:element>  
                    
                </xsl:if>
                <xsl:if test="@CDNUMREN">
                    <xsl:element name="cdnumren">
                        <xsl:value-of select="@CDNUMREN" />
                    </xsl:element>  
                </xsl:if>
                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>  
                </xsl:if>
                <xsl:if test="@DSTIPSIT">
                    <xsl:element name="dstipsit">
                        <xsl:value-of select="@DSTIPSIT" />
                    </xsl:element>  
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
