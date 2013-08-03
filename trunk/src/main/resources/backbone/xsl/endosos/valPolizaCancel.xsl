<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <poliza-cancel-vO xsi:type="java:mx.com.aon.flujos.endoso.model.PolizaCancelVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam" />
    		<xsl:element name="desc-motivo-anulacion">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_dsmotanu_o']/@value" />
			</xsl:element>
			<xsl:element name="fecha-cancelacion">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_fecancel_o']/@value" />
			</xsl:element>
			<xsl:element name="msg-id">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
			</xsl:element>
			<xsl:element name="title">
    			<xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
			</xsl:element>
        </poliza-cancel-vO>
    </xsl:template>
</xsl:stylesheet>