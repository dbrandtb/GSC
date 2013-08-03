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
				xsi:type="java:mx.com.aon.portal.model.OrdenDeCompraVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDCARRO">
					<xsl:element name="cd-carro">
						<xsl:value-of select="@CDCARRO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEINICIO">
					<xsl:element name="fe-inicio">
						<xsl:value-of select="@FEINICIO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMTARJ">
					<xsl:element name="nm-tarj">
						<xsl:value-of select="@NMTARJ" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDCONTRA">
					<xsl:element name="cd-contra">
						<xsl:value-of select="@CDCONTRA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDASEGUR">
					<xsl:element name="cd-asegur">
						<xsl:value-of select="@CDASEGUR" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMSUBTOT">
					<xsl:element name="nm-sub-tot">
						<xsl:value-of select="@NMSUBTOT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMDSC">
					<xsl:element name="nm-dsc">
						<xsl:value-of select="@NMDSC" />
					</xsl:element>
				</xsl:if>		
				<xsl:if test="@NMTOTAL">
					<xsl:element name="nm-total">
						<xsl:value-of select="@NMTOTAL" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDESTADO">
					<xsl:element name="cd-estado">
						<xsl:value-of select="@CDESTADO" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@DSESTADO">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSESTADO" />
                    </xsl:element>
                </xsl:if>                
				<xsl:if test="@FEESTADO">
					<xsl:element name="fe-estado">
						<xsl:value-of select="@FEESTADO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMORDEN">
					<xsl:element name="nm-orden">
						<xsl:value-of select="@NMORDEN" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDTIPDOM">
					<xsl:element name="cd-tip-dom">
						<xsl:value-of select="@CDTIPDOM" />
					</xsl:element>
				</xsl:if>				
				<xsl:if test="@NMORDDOM">
					<xsl:element name="nm-ord-don">
						<xsl:value-of select="@NMORDDOM" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDCLIENT">
					<xsl:element name="cd-client">
						<xsl:value-of select="@CDCLIENT" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDFORPAG">
					<xsl:element name="cd-for-pag">
						<xsl:value-of select="@CDFORPAG" />
					</xsl:element>
				</xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>