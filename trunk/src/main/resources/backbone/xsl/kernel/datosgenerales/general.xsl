<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.royalsun.vo.ResultadoDAO">
			<xsl:choose>
				<xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">
					<valores xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_OTTIPSUP
						</key>
						<value xsi:type="java:java.lang.String">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_OTTIPSUP']/@value"/>
						</value>
					</valores>
					<valores xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_TIPSUP_WORK
						</key>
						<value xsi:type="java:java.lang.Integer">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPSUP_WORK']/@value"/>
						</value>
					</valores>
					<valores xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
						<key xsi:type="java:java.lang.String">
						     po_NSUPLOGI
						</key>
						<value xsi:type="java:java.lang.Integer">
							<xsl:value-of select="result/storedProcedure/outparam[@id='po_NSUPLOGI']/@value"/>
						</value>
					</valores>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="error"/>
				</xsl:otherwise>
			</xsl:choose>
		</resultado-dao>
	</xsl:template>
	<xsl:template name="error">
		<xsl:element name="cd-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_cderror']/@value"/>
		</xsl:element>
		<xsl:element name="ds-error">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_dserror']/@value"/>
		</xsl:element>
		<xsl:element name="estado">
			<xsl:value-of select="result/storedProcedure/outparam[@id='po_tipoerror']/@value"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
