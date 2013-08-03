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
					<xsl:if test="@NMSITUAC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMSITUAC
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@NMSITUAC"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDGARANT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDGARANT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDGARANT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSGARANT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSGARANT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSGARANT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDCONTAR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDCONTAR
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDCONTAR"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSCONTAR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSCONTAR
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSCONTAR"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPCON">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPCON
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPCON"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@POCOMPRO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								POCOMPRO
							</key>
							<value xsi:type="java:java.lang.Double">
								<xsl:value-of select="@POCOMPRO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMIMPORT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMIMPORT
							</key>
							<value xsi:type="java:java.lang.Double">
								<xsl:value-of select="@NMIMPORT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PTIMPORT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PTIMPORT
							</key>
							<value xsi:type="java:java.lang.Double">
								<xsl:value-of select="@PTIMPORT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PTIMPREC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PTIMPREC
							</key>
							<value xsi:type="java:java.lang.Double">
								<xsl:value-of select="@PTIMPREC"/>
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
