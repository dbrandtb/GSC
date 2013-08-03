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
				xsi:type="java:mx.com.aon.portal.model.ConfigurarFormaCalculoAtributoVariableVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDIDEN">
					<xsl:element name="cd-iden">
						<xsl:value-of select="@CDIDEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDELEMENTO">
					<xsl:element name="cd-elemento">
						<xsl:value-of select="@CDELEMENTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSELEMEN">
					<xsl:element name="ds-elemen">
						<xsl:value-of select="@DSELEMEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPSIT">
					<xsl:element name="cd-tip-sit">
						<xsl:value-of select="@CDTIPSIT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTIPSIT">
					<xsl:element name="ds-tip-sit">
						<xsl:value-of select="@DSTIPSIT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDUNIECO">
					<xsl:element name="cd-unieco">
						<xsl:value-of select="@CDUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSUNIECO">
					<xsl:element name="ds-unieco">
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
				<xsl:if test="@CDTABLA">
					<xsl:element name="cd-tabla">
						<xsl:value-of select="@CDTABLA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWFORMACALCULO">
					<xsl:element name="sw-forma-calculo">
						<xsl:value-of select="@SWFORMACALCULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@ATRIBUTO">
					<xsl:element name="atributo">
						<xsl:value-of select="@ATRIBUTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CALCULO">
					<xsl:element name="calculo">
						<xsl:value-of select="@CALCULO" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>