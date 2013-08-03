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
				xsi:type="java:mx.com.aon.portal.model.GrupoPersonaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDGRUPO">
					<xsl:element name="cd-grupo">
						<xsl:value-of select="@CDGRUPO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDAGRGRUPO">
					<xsl:element name="cd-agr-grupo">
						<xsl:value-of select="@CDAGRGRUPO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDAGRUPA">
					<xsl:element name="cd-agrupa">
						<xsl:value-of select="@CDAGRUPA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDGRUPOPER">
					<xsl:element name="cd-grupo-per">
						<xsl:value-of select="@CDGRUPOPER" />
					</xsl:element>
				</xsl:if>

				<xsl:if test="@CDPOLMTRA">
					<xsl:element name="cd-pol-mtra">
						<xsl:value-of select="@CDPOLMTRA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMPOLIEX">
					<xsl:element name="nm-poli-ex">
						<xsl:value-of select="@NMPOLIEX" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEINICIO">
					<xsl:element name="fe-inicio">
						<xsl:value-of select="@FEINICIO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEFIN">
					<xsl:element name="fe-fin">
						<xsl:value-of select="@FEFIN" />
					</xsl:element>
				</xsl:if>

			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>