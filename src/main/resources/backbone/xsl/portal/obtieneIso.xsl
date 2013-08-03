<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:template match="/">
        <iso-vO xsi:type="java:mx.com.aon.portal.model.IsoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="pais">
                <xsl:value-of select="result/storedProcedure/outparam[@id='P_PAIS']/@value" />
            </xsl:element>
            <xsl:element name="languague">
                <xsl:value-of select="result/storedProcedure/outparam[@id='P_LANGUAGUE_ISO']/@value" />
            </xsl:element>
              <xsl:element name="formato-numerico">
                <xsl:value-of select="result/storedProcedure/outparam[@id='P_DSFORMATONUMERICO']/@value" />
            </xsl:element>
              <xsl:element name="formato-fecha">
                <xsl:value-of select="result/storedProcedure/outparam[@id='P_DSFORMATOFECHA']/@value" />
            </xsl:element>

            <xsl:element name="cd-region">
                <xsl:value-of select="result/storedProcedure/outparam[@id='REGIONID']/@value" />
            </xsl:element>
            <xsl:element name="cd-idioma">
                <xsl:value-of select="result/storedProcedure/outparam[@id='LANGCODE']/@value" />
            </xsl:element>
              <xsl:element name="client-date-format">
                <xsl:value-of select="result/storedProcedure/outparam[@id='P_DSFORMATOFECHAC']/@value" />
            </xsl:element>
            
        </iso-vO>

    </xsl:template>
</xsl:stylesheet>
