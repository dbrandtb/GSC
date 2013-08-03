<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
			<xsl:element name="estado">
				<xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
			</xsl:element>
		</resultado-dao>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<cursor xsi:type="java:mx.com.royalsun.vo.DomicilioVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
					<xsl:if test="@NMORDDOM">
                            <xsl:element name="domicilio">
								<xsl:value-of select="@NMORDDOM"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@CDTIPDOM">
                            <xsl:element name="tipo">
								<xsl:value-of select="@CDTIPDOM"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@DSDOMICI">
                            <xsl:element name="desc-tipo">
								<xsl:value-of select="@DSDOMICI"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@NMNUMERO">
                            <xsl:element name="numero">
								<xsl:value-of select="@NMNUMERO"/>
							</xsl:element>						
					</xsl:if>
					<xsl:if test="@CDPOSTAL">
                            <xsl:element name="codigo-postal">
								<xsl:value-of select="@CDPOSTAL"/>
							</xsl:element>						
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
