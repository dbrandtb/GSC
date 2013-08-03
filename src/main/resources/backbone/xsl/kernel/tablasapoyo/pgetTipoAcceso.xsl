<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:element name="parametros">
			    <xsl:value-of select="result/storedProcedure/outparam[@id='tipoac']/@value"/>
			</xsl:element>
			<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@ottipoac">
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
							xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
							ottipoac
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="@ottipoac" />
						</value>
					</values>
				</xsl:if>
			</cursor>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
