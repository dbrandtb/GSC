<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDPERSON
						</key>
						<value xsi:type="java:java.lang.Long">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDPERSON']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSNOMBRE
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSNOMBRE']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSNOMBR1
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSNOMBR1']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSNOMBR2
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSNOMBR2']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSAPELL1
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSAPELL1']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSAPELL2
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSAPELL2']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDPERSON
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDPERSON']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_CDTIPPER
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDTIPPER']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTFISJUR
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTFISJUR']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_DSFISJUR
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSFISJUR']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTSEXO
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTSEXO']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_FENACIMI
						</key>
						<value xsi:type="java:java.util.Date">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_FENACIMI']/@value"/>
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
