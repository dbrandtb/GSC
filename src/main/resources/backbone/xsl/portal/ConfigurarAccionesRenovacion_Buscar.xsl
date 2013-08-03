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
				xsi:type="java:mx.com.aon.portal.model.ConfigurarAccionRenovacionVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDRENOVA">
					<xsl:element name="cd-renova">
						<xsl:value-of select="@CDRENOVA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDROL">
					<xsl:element name="cd-rol">
						<xsl:value-of select="@CDROL" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSSISROL">
					<xsl:element name="ds-rol">
						<xsl:value-of select="@DSSISROL" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@ID_TITULO">
					<xsl:element name="cd-titulo">
						<xsl:value-of select="@ID_TITULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTITULO">
					<xsl:element name="ds-titulo">
						<xsl:value-of select="@DSTITULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDCAMPO">
					<xsl:element name="cd-campo">
						<xsl:value-of select="@CDCAMPO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSCAMPO">
					<xsl:element name="ds-campo">
						<xsl:value-of select="@DSCAMPO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDACCION">
					<xsl:element name="cd-accion">
						<xsl:value-of select="@CDACCION" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSACCION">
					<xsl:element name="ds-accion">
						<xsl:value-of select="@DSACCION" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>