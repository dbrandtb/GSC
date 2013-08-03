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
				xsi:type="java:mx.com.aon.portal.model.ConsultaConfiguracionRenovacionVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@DSELEMEN">
					<xsl:element name="ds-elemen">
						<xsl:value-of select="@DSELEMEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSUNIECO">
					<xsl:element name="ds-uni-eco">
						<xsl:value-of select="@DSUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSRAMO">
					<xsl:element name="ds-ramo">
						<xsl:value-of select="@DSRAMO" />
					</xsl:element>
				</xsl:if>				
				<xsl:if test="@TIPO">
					<xsl:element name="ds-tipo-renova">
						<xsl:value-of select="@TIPO" />
					</xsl:element>
				</xsl:if>
  	 		</item-list>
 	  	</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>