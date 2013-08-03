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
				xsi:type="java:mx.com.aon.catbo.model.ClienteVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@DSELEMEN">
					<xsl:element name="dselemen">
						<xsl:value-of select="@DSELEMEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDPERSON">
					<xsl:element name="cdperson">
						<xsl:value-of select="@CDPERSON" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPIDE">
					<xsl:element name="cdtipide">
						<xsl:value-of select="@CDTIPIDE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDIDEPER">
					<xsl:element name="cdideper">
						<xsl:value-of select="@CDIDEPER" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSNOMBRE">
					<xsl:element name="dsnombre">
						<xsl:value-of select="@DSNOMBRE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPPER">
					<xsl:element name="cdtipper">
						<xsl:value-of select="@CDTIPPER" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@OTFISJUR">
					<xsl:element name="otfisjur">
						<xsl:value-of select="@OTFISJUR" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@OTSEXO">
					<xsl:element name="otsexo">
						<xsl:value-of select="@OTSEXO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FENACIMI">
					<xsl:element name="fenacimi">
						<xsl:value-of select="@FENACIMI" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDRFC">
					<xsl:element name="cdrfc">
						<xsl:value-of select="@CDRFC" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDELEMEN">
					<xsl:element name="cdelemen">
						<xsl:value-of select="@CDELEMEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@TELEFONO_CASA">
					<xsl:element name="telefonocasa">
						<xsl:value-of select="@TELEFONO_CASA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@TELEFONO_OFIC">
					<xsl:element name="telefonoofic">
						<xsl:value-of select="@TELEFONO_OFIC" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@MAIL_PERSONAL">
					<xsl:element name="mailpersonal">
						<xsl:value-of select="@MAIL_PERSONAL" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@MAIL_OFICINA">
					<xsl:element name="mailoficina">
						<xsl:value-of select="@MAIL_OFICINA" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>