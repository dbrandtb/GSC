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
				xsi:type="java:mx.com.aon.portal.model.CampoCatalogoVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDCOLUMN">
					<xsl:element name="cd-column">
						<xsl:value-of select="@CDCOLUMN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSCOLUMN">
					<xsl:element name="ds-column">
						<xsl:value-of select="@DSCOLUMN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWFORMAT">
					<xsl:element name="sw-format">
						<xsl:value-of select="@SWFORMAT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMMINLG">
					<xsl:element name="nm-min-lg">
						<xsl:value-of select="@NMMINLG" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMMAXLG">
					<xsl:element name="nm-max-lg">
						<xsl:value-of select="@NMMAXLG" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSLISTA">
					<xsl:element name="ds-lista">
						<xsl:value-of select="@DSLISTA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWFILTRO">
					<xsl:element name="sw-filtro">
						<xsl:value-of select="@SWFILTRO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWMANDAT">
					<xsl:element name="sw-mandat">
						<xsl:value-of select="@SWMANDAT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWVISIBLE">
					<xsl:element name="sw-visible">
						<xsl:value-of select="@SWVISIBLE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWLLAVE">
					<xsl:element name="sw-llave">
						<xsl:value-of select="@SWLLAVE" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>