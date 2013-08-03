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
					<xsl:if test="@CDESTADO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDESTADO
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDESTADO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@STATUS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								STATUS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@STATUS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPSIT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPSIT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPSIT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWREDUCI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWREDUCI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWREDUCI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDAGRUPA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDAGRUPA
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDAGRUPA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEFECSIT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEFECSIT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEFECSIT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FECHAREF">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FECHAREF
							</key>
							<value xsi:type="java:java.lan.String">
								<xsl:value-of select="@FECHAREF"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@INDPARBE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								INDPARBE
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@INDPARBE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEINIPBS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEINIPBS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEINIPBS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PORPARBE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PORPARBE
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@PORPARBE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@INTFINAN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								INTFINAN
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@INTFINAN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDMOTANU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDMOTANU
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDMOTANU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEINISUS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEINISUS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEINISUS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEFINSUS">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEINISUS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEFINSUS"/>
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
