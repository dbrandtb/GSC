<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='Gv_Identificador_Error']/@value=''">
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     Gv_Rowid
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='Gv_Rowid']/@value"/>
						</value>
					</values>
		            <xsl:element name="estado">S</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
		</resultado-dao>
	</xsl:template>
	<xsl:template name="error">
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='Gv_Identificador_Error']/@value"/>
		</xsl:element>
        <xsl:element name="estado">O</xsl:element>
	</xsl:template>
</xsl:stylesheet>
