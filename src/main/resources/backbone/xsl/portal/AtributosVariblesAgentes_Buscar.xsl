<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<wrapper-resultados
			xsi:type="java:mx.com.aon.portal.util.WrapperResultados"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates
				select="result/storedProcedure/outparam" />
		</wrapper-resultados>
	</xsl:template>


	<xsl:template match="rows">
		<xsl:for-each select="row">
			<item-list
				xsi:type="java:mx.com.aon.portal.model.AtributoVaribleAgenteVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDATRIBU">
					<xsl:element name="cd-atribu">
						<xsl:value-of select="@CDATRIBU" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSATRIBU">
					<xsl:element name="ds-atribu">
						<xsl:value-of select="@DSATRIBU" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWFORMAT">
					<xsl:element name="sw-format">
						<xsl:value-of select="@SWFORMAT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMLMAX">
					<xsl:element name="nml-max">
						<xsl:value-of select="@NMLMAX" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMLMIN">
					<xsl:element name="nml-min">
						<xsl:value-of select="@NMLMIN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWOBLIGA">
					<xsl:element name="sw-obliga">
						<xsl:value-of select="@SWOBLIGA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@OTTABVAL">
					<xsl:element name="ot-tab-val">
						<xsl:value-of select="@OTTABVAL" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@GB_SWFORMAT">
					<xsl:element name="gb-sw-format">
						<xsl:value-of select="@GB_SWFORMAT" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>