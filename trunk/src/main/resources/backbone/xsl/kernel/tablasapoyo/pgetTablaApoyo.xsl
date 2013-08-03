<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
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
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@OTCLAVE1">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otclave1
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTCLAVE1"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTCLAVE2">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otclave2
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTCLAVE2"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTCLAVE3">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otclave3
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTCLAVE3"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTCLAVE4">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otclave4
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTCLAVE4"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTCLAVE5">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otclave5
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTCLAVE5"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEDESDE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								fedesde
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEDESDE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEHASTA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								fehasta
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEHASTA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR01">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor01
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR01"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR02">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor02
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR02"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR03">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor03
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR03"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR04">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor04
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR04"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR05">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor05
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR05"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR06">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor06
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR06"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR07">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor07
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR07"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR08">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor08
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR08"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR09">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor09
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR09"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR10">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor10
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR10"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR11">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor11
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR11"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR12">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor12
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR12"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR13">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor13
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR13"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR14">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor14
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR14"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR15">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor15
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR15"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR16">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor16
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR16"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR17">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor17
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR17"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR18">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor18
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR18"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR19">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor19
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR19"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR20">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor20
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR20"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR21">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor21
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR21"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR22">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor22
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR22"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR23">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor23
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR23"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR24">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor24
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR24"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR25">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor25
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR25"/>
							</value>
						</values>
					</xsl:if>
			</cursor>
		</xsl:for-each>
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
