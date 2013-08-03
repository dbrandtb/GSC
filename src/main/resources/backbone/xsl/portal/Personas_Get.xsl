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
				xsi:type="java:mx.com.aon.portal.model.PersonaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@OTFISJUR">
					<xsl:element name="ot-fis-jur">
						<xsl:value-of select="@OTFISJUR" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSFISJUR">
					<xsl:element name="ds-fis-jur">
						<xsl:value-of select="@DSFISJUR" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSNOMBRE">
					<xsl:element name="ds-nombre">
						<xsl:value-of select="@DSNOMBRE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSAPELLIDO">
					<xsl:element name="ds-apellido">
						<xsl:value-of select="@DSAPELLIDO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSAPELLIDO1">
					<xsl:element name="ds-apellido1">
						<xsl:value-of select="@DSAPELLIDO1" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@OTSEXO">
					<xsl:element name="ot-sexo">
						<xsl:value-of select="@OTSEXO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDESTCIV">
					<xsl:element name="cd-est-civ">
						<xsl:value-of select="@CDESTCIV" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FENACIMI">
					<xsl:element name="fe-nacimi">
						<xsl:value-of select="@FENACIMI" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDNACION">
					<xsl:element name="cd-nacion">
						<xsl:value-of select="@CDNACION" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPPER">
					<xsl:element name="cd-tip-per">
						<xsl:value-of select="@CDTIPPER" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTIPPER">
					<xsl:element name="ds-tip-per">
						<xsl:value-of select="@DSTIPPER" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPIDE">
					<xsl:element name="cd-tip-ide">
						<xsl:value-of select="@CDTIPIDE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDIDEPER">
					<xsl:element name="cd-ide-per">
						<xsl:value-of select="@CDIDEPER" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEINGRESO">
					<xsl:element name="fe-ingreso">
						<xsl:value-of select="@FEINGRESO" />
					</xsl:element>
				</xsl:if>

				<xsl:if test="@CURP">
					<xsl:element name="curp">
						<xsl:value-of select="@CURP" />
					</xsl:element>
				</xsl:if>

				<xsl:if test="@CDRFC">
					<xsl:element name="cd-rfc">
						<xsl:value-of select="@CDRFC" />
					</xsl:element>
				</xsl:if>

				<xsl:if test="@DSEMAIL">
					<xsl:element name="ds-email">
						<xsl:value-of select="@DSEMAIL" />
					</xsl:element>
				</xsl:if>

			</item-list>
		</xsl:for-each>
	</xsl:template>


</xsl:stylesheet>