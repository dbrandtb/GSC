<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='p_status']/@value"/>
			</xsl:element>
			<xsl:element name="ds-error">
				<xsl:value-of select="result/storedProcedure/outparam[@id='p_errormsg']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
</xsl:stylesheet>
