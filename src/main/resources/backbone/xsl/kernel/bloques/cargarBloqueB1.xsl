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
					<xsl:if test="@CDUNIECO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDUNIECO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDUNIECO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDRAMO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDRAMO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDRAMO"/>
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
					<xsl:if test="@NMPOLIZA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMPOLIZA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMPOLIZA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMSUPLEM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMSUPLEM
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMSUPLEM"/>
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
					<xsl:if test="@SWESTADO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWESTADO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWESTADO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMSOLICI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMSOLICI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMSOLICI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEAUTORI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEAUTORI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEAUTORI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDMOTANU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDMOTANU
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDMOTANU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEANULAC">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEANULAC
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEANULAC"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWAUTORI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWAUTORI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWAUTORI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDMONEDA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDMONEDA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDMONEDA"/>
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
								FEFINSUS
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEFINSUS"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTTEMPOT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTTEMPOT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTTEMPOT"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEEFECTO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEEFECTO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEEFECTO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@HHEFECTO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								HHEFECTO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@HHEFECTO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEPROREN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEPROREN
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEPROREN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEVENCIM">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEVENCIM
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEVENCIM"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMRENOVA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMRENOVA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMRENOVA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FERECIBO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FERECIBO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FERECIBO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEULTSIN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEULTSIN
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEULTSIN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMNUMSIN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMNUMSIN
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMNUMSIN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPCOA">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPCOA
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPCOA"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWTARIFI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWTARIFI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWTARIFI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWABRIDO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWABRIDO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWABRIDO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@FEEMISIO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								FEEMISIO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@FEEMISIO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDPERPAG">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDPERPAG
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDPERPAG"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMPOLIEX">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMPOLIEX
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMPOLIEX"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMCUADRO">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMCUADRO
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMCUADRO"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PORREDAU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PORREDAU
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@PORREDAU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWCONSOL">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWCONSOL
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWCONSOL"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMPOLCOI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMPOLCOI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMPOLCOI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@ADPARBEN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								ADPARBEN
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@ADPARBEN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMCERCOI">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMCERCOI
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@NMCERCOI"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDTIPREN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDTIPREN
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDTIPREN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@CDAGENTE_AP">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDAGENTE_AP
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CDAGENTE_AP"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@PORREDAU_AP">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								PORREDAU_AP
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@PORREDAU_AP"/>
							</value>
						</values>
					</xsl:if>			
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
