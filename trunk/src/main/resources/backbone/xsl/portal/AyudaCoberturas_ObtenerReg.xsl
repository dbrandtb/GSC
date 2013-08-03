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
				xsi:type="java:mx.com.aon.portal.model.AyudaCoberturasVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDGARANTIAXCIA">
					<xsl:element name="cd-garantiax-cia">
						<xsl:value-of select="@CDGARANTIAXCIA" />
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
				<xsl:if test="@CDTIPRAM">
					<xsl:element name="cd-tipram">
						<xsl:value-of select="@CDTIPRAM" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTIPRAM">
					<xsl:element name="ds-tipram">
						<xsl:value-of select="@DSTIPRAM" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDSUBRAM">
					<xsl:element name="cd-subram">
						<xsl:value-of select="@CDSUBRAM" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSSUBRAM">
					<xsl:element name="ds-subram">
						<xsl:value-of select="@DSSUBRAM" />
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
				<xsl:if test="@CDGARANT">
					<xsl:element name="cd-garant">
						<xsl:value-of select="@CDGARANT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSGARANT">
					<xsl:element name="ds-garant">
						<xsl:value-of select="@DSGARANT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSAYUDA">
					<xsl:element name="ds-ayuda">
						<xsl:value-of select="@DSAYUDA" />
					</xsl:element>
				</xsl:if>				
				<xsl:if test="@LANG_CODE">
					<xsl:element name="lang-code">
						<xsl:value-of select="@LANG_CODE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@LANG_NAME">
					<xsl:element name="lang-name">
						<xsl:value-of select="@LANG_NAME" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>