<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:template match="/">
        <label-vo xsi:type="java:mx.com.aon.portal.model.BaseObjectVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="value">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_swendoso_o']/@value" />
            </xsl:element>
            <xsl:element name="label">
                <xsl:value-of select="result/storedProcedure/outparam[@id='']/@value" />
            </xsl:element>
        </label-vo>
    </xsl:template>
</xsl:stylesheet>