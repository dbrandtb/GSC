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
				xsi:type="java:mx.com.aon.portal.model.MecanismoDeTooltipVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@IDTITULO">
					<xsl:element name="id-titulo">
						<xsl:value-of select="@IDTITULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTITULO">
					<xsl:element name="ds-titulo">
						<xsl:value-of select="@DSTITULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@IDOBJETO">
					<xsl:element name="id-objeto">
						<xsl:value-of select="@IDOBJETO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NBOBJETO">
					<xsl:element name="nb-objeto">
						<xsl:value-of select="@NBOBJETO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FGTIPOBJ">
					<xsl:element name="fg-tip-obj">
						<xsl:value-of select="@FGTIPOBJ" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NBETIQUE">
					<xsl:element name="nb-etiqueta">
						<xsl:value-of select="@NBETIQUE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTOOLTP">
					<xsl:element name="ds-tooltip">
						<xsl:value-of select="@DSTOOLTP" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FGAYUDA">
					<xsl:element name="fg-ayuda">
						<xsl:value-of select="@FGAYUDA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSAYUDA">
					<xsl:element name="ds-ayuda">
						<xsl:value-of select="@DSAYUDA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@LANG_CODE">
					<xsl:element name="lang_-code">
						<xsl:value-of select="@LANG_CODE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NBCAJAIZQUIERDA">
					<xsl:element name="nb-caja-izquierda">
						<xsl:value-of select="@NBCAJAIZQUIERDA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NBCAJADERECHA">
					<xsl:element name="nb-caja-derecha">
						<xsl:value-of select="@NBCAJADERECHA" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>