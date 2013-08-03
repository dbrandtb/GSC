<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <base-object-vO xsi:type="java:mx.com.aon.flujos.endoso.model.TarificarVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam" />
    		<xsl:element name="msg-id">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
			</xsl:element>
			<xsl:element name="title">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
			</xsl:element>
            <xsl:element name="imp-ant">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_ptimpant_o']/@value" />
            </xsl:element>
            <xsl:element name="imp-act">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_ptimpact_o']/@value" />
            </xsl:element>
            <xsl:element name="imp-dif">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_ptimpdif_o']/@value" />
            </xsl:element>
        </base-object-vO>
    </xsl:template>
</xsl:stylesheet>