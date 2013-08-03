<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDPAIS
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDPAIS']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSPAIS
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSPAIS']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDPROVIN
						</key>
						<value xsi:type="java:java.lang.Integer">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDPROVIN']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSPROVIN
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSPROVIN']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDMUNICI
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDMUNICI']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSMUNICI
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSMUNICI']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDCIUDAD
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDCIUDAD']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSCIUDAD
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSCIUDAD']/@value"/>
						</value>
					</values>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
	
	<xsl:template name="error">
		<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
