<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <lista-de-valores-vO xsi:type="java:mx.com.aon.flujos.endoso.model.SuplemVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam" />
            <xsl:element name="nm-suplem">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_nmsuplem_o']/@value" />
            </xsl:element>
            <xsl:element name="nsup-logi">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_nsuplogi_o']/@value" />
            </xsl:element>
            <xsl:element name="fesolici">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_fesolici_o']/@value" />
            </xsl:element>
            <xsl:element name="feinival">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_feinival_o']/@value" />
            </xsl:element>
        </lista-de-valores-vO>
    </xsl:template>         
</xsl:stylesheet>  