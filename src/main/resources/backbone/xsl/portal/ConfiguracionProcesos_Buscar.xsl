<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<wrapper-resultados
			xsi:type="java:mx.com.aon.portal.util.WrapperResultados"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:element name="msg-id">
				<xsl:value-of
					select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
			</xsl:element>
			<xsl:element name="msg">
				<xsl:value-of
					select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
			</xsl:element>

			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />

		</wrapper-resultados>
	</xsl:template>


	<xsl:template match="rows">
		<xsl:for-each select="row">
			<item-list
				xsi:type="java:mx.com.aon.portal.model.ConfiguracionProcesoVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDUNIECO">
					<xsl:element name="cd-uni-eco">
						<xsl:value-of select="@CDUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSUNIECO">
					<xsl:element name="ds-uni-eco">
						<xsl:value-of select="@DSUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDRAMO">
					<xsl:element name="cd-ramo">
						<xsl:value-of select="@CDRAMO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSRAMO">
					<xsl:element name="ds-ramo">
						<xsl:value-of select="@DSRAMO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDELEMENTO">
					<xsl:element name="cd-elemento">
						<xsl:value-of select="@CDELEMENTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSELEMEN">
					<xsl:element name="ds-elemento">
						<xsl:value-of select="@DSELEMEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDPROCESO">
					<xsl:element name="cd-proceso">
						<xsl:value-of select="@CDPROCESO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSPROCESO">
					<xsl:element name="ds-proceso">
						<xsl:value-of select="@DSPROCESO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWESTADO">
					<xsl:element name="sw-estado">
						<xsl:value-of select="@SWESTADO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSESTADO">
					<xsl:element name="ds-estado">
						<xsl:value-of select="@DSESTADO" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>