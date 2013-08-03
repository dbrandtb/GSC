<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
		<guardar-bloque-resultado-dao xsi:type="java:mx.com.ice.services.to.GuardarBloqueResultadoDAO">
			<xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='Gv_Identificador_Error']/@value!='']">
                    <xsl:call-template name="error" />
                </xsl:when>
				<xsl:otherwise>
		            <xsl:element name="estado">S</xsl:element>
				</xsl:otherwise>
            </xsl:choose>
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				     p_ERROR
				</key>
				<value xsi:type="java:java.lang.String">
					<xsl:value-of select="result/storedProcedure/outparam[@id='Gv_Identificador_Error']/@value"/>
				</value>
			</values>
        </guardar-bloque-resultado-dao>
    </xsl:template>

    <xsl:template name="error">
		<xsl:element name="estado">O</xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='Gv_Identificador_Error']/@value" />
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
