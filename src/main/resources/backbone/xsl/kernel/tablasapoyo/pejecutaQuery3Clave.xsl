<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/ @value='S'">
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
					<xsl:if test="@otvalor01">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor01
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor01"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor02">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor02
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor02"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor03">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor03
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor03"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor04">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor04
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor04"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor05">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor05
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor05"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor06">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor06
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor06"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor07">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor07
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor07"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor08">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor08
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor08"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor09">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor09
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor09"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor10">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor10
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor10"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor11">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor11
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor11"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor12">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor12
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor12"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor13">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor13
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor13"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor14">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor14
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor14"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor15">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor15
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor15"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor16">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor16
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor16"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor17">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor17
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor17"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor18">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor18
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor18"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor19">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor19
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor19"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor20">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor20
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor20"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor21">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor21
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor21"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor22">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor22
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor22"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor23">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor23
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor23"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor24">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor24
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor24"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@otvalor25">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								otvalor25
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@otvalor25"/>
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
