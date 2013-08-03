<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<!-- <xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">-->
				
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
				<!-- </xsl:when>
				 <xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>-->
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
			
			<!-- temporal -->
			<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
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
					<xsl:if test="@CDATRIBU">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CDATRIBU
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@CDATRIBU"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@OTVALOR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								OTVALOR
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@OTVALOR"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMIN">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMIN
							</key>
							<value xsi:type="java:java.lang.Integer">
								<xsl:value-of select="@NMLMIN"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@NMLMAX">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								NMLMAX
							</key>
							<value xsi:type="java:java.lang.Long">
								<xsl:value-of select="@NMLMAX"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@SWFORMAT">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								SWFORMAT
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@SWFORMAT"/>
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
