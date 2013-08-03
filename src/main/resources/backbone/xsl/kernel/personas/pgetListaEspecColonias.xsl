<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_tipoError' and @value='S']">
					<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<values xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:for-each select="row">
				<resultado-dAO xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
					<xsl:if test="@CLAVE">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								CLAVE
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@CLAVE"/>
							</value>
						</values>
					</xsl:if>
					<xsl:if test="@VALOR">
						<values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
							<key xsi:type="java:java.lang.String">
								VALOR
							</key>
							<value xsi:type="java:java.lang.String">
								<xsl:value-of select="@VALOR"/>
							</value>
						</values>
					</xsl:if>
				</resultado-dAO>
			</xsl:for-each>
		</values>
	</xsl:template>
	<xsl:template name="error">
		<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_cdError']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_dsError']/@value"/>
		</xsl:element>
		<xsl:element name="estado">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_tipoError']/@value"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
