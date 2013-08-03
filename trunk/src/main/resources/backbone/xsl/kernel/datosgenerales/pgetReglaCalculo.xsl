<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR01
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR01']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR02
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR02']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR03
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR03']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR04
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR04']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR05
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR05']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR06
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR06']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR07
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR07']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR08
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR08']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR09
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR09']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR10
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR10']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR11
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR11']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR12
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR12']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR13
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR13']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR14
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR14']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR15
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR15']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR16
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR16']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR17
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR17']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR18
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR18']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR19
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR19']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR20
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR20']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR21
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR21']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR22
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR22']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR23
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR23']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR24
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR24']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR25
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR25']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTVALOR26
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTVALOR26']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_FEDESDE
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_FEDESDE']/@value"/>
						</value>
					</values>
					<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_FEHASTA
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_FEHASTA']/@value"/>
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
