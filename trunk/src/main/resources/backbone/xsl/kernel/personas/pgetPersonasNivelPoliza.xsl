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
					<xsl:if test="@CDTIPIDE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdtipide
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPIDE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDIDEPER">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdideper
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDIDEPER"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPERSON">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdperson
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@CDPERSON"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSNOMBRE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								dsnombre
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSNOMBRE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDROL">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdrol
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDROL"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSROL">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								dsrol
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSROL"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMORDDOM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								nmorddom
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMORDDOM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSDOMICI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								dsdomici
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSDOMICI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDAGRUPA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdagrupa
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDAGRUPA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDFORPAG">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdforpag
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDFORPAG"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDBANCO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdbanco
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDBANCO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDSUCURS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdsucurs
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDSUCURS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDCUENTA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdcuenta
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDCUENTA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDRAZON">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdrazon
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDRAZON"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWREGULA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								swregula
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWREGULA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPERREG">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdperreg
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDPERREG"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEULTREG">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								feultreg
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEULTREG"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDGESTOR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdgestor
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDGESTOR"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPRED">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdtipred
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPRED"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEVENCIM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								fevencim
							</key>
							<value xsi:type="java:java.util.Date">
								<xsl:value-of select="@FEVENCIM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTARCRE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								cdtarcre
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTARCRE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMCUOTA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								nmcuota
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMCUOTA"/>
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
