<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.services.to.GuardarBloqueResultadoDAO">
                                      
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>
            
            <xsl:element name="error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            
            <xsl:element name="cd-error">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>
            
            <xsl:element name="nm-objeto">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_nmobjeto_o']/@value" />
            </xsl:element>
            
            <xsl:element name="nm-obj">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_nmobjeto_o']/@value" />
            </xsl:element>

        </resultado-dao>
    </xsl:template>


</xsl:stylesheet>
