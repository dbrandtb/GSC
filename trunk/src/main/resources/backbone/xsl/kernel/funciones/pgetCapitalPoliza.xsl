<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
				<key xsi:type="java:java.lang.String">
				    SVCAP
				</key>
				<value xsi:type="java:java.lang.Long">
					<xsl:value-of select="result/storedProcedure/outparam[@id='SVCAP']/@value"/>
				</value>
			</values>
		</resultado-dao>
	</xsl:template>
</xsl:stylesheet>
