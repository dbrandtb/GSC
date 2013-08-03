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
					<xsl:if test="@CDPERSON">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDPERSON
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@CDPERSON"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMORDDOM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMORDDOM
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMORDDOM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPDOM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPDOM
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPDOM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSDOMICI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSDOMICI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSDOMICI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDSIGLAS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDSIGLAS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDSIGLAS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SIGLAS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SIGLAS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SIGLAS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDIDIOMA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDIDIOMA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDIDIOMA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@IDIOMA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								IDIOMA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@IDIOMA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMTELEFO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMTELEFO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMTELEFO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPOSTAL">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDPOSTAL
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDPOSTAL"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTPOBLAC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTPOBLAC
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTPOBLAC"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPAIS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDPAIS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDPAIS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PAIS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PAIS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@PAIS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTPISO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTPISO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTPISO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMNUMERO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMNUMERO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMNUMERO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPROVIN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDPROVIN
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDPROVIN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@ESTADO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								ESTADO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@ESTADO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@DSZONA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								DSZONA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@DSZONA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDCOLONI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDCOLONI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDCOLONI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@COLONIA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								COLONIA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@COLONIA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@MUNICIPIO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								MUNICIPIO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@MUNICIPIO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CIUDAD">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CIUDAD
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CIUDAD"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@TIPDOMI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								TIPDOMI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@TIPDOMI"/>
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
