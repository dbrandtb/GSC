<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

    <xsl:template match="/">
        <opcion-vO xsi:type="java:mx.com.aon.portal.model.configworkflow.WorkFlowPs1VO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam" />
            <xsl:element name="out-param-text">
                <xsl:value-of select="result/storedProcedure/outparam[@id='PV_TITLE_O']/@value" />
            </xsl:element>
        </opcion-vO>
    </xsl:template>
</xsl:stylesheet>