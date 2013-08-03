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
				xsi:type="java:mx.com.aon.portal.model.AlertaUsuarioVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDIDUNICO">
					<xsl:element name="cd-id-unico">
						<xsl:value-of select="@CDIDUNICO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDELEMENTO">
					<xsl:element name="cd-elemento">
						<xsl:value-of select="@CDELEMENTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSNOMBRE">
					<xsl:element name="ds-nombre">
						<xsl:value-of select="@DSNOMBRE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDPROCESO">
					<xsl:element name="cd-proceso">
						<xsl:value-of select="@CDPROCESO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSPROCES">
					<xsl:element name="ds-proceso">
						<xsl:value-of select="@DSPROCES" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDUSUARIO">
					<xsl:element name="cd-usuario">
						<xsl:value-of select="@CDUSUARIO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDUNIECO">
					<xsl:element name="cd-uni-eco">
						<xsl:value-of select="@CDUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSUNIECO">
					<xsl:element name="ds-uni-eco">
						<xsl:value-of select="@DSUNIECO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDPRODUCTO">
					<xsl:element name="cd-Producto">
						<xsl:value-of select="@CDPRODUCTO" />
					</xsl:element>
				</xsl:if>				
				<xsl:if test="@DSPRODUCTO">
					<xsl:element name="ds-producto">
						<xsl:value-of select="@DSPRODUCTO" />
					</xsl:element>
				</xsl:if>					
				<xsl:if test="@NMPOLIZA">
					<xsl:element name="nm-poliza">
						<xsl:value-of select="@NMPOLIZA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMRECIBO">
					<xsl:element name="nm-recibo">
						<xsl:value-of select="@NMRECIBO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEULTIMOEVENTO">
					<xsl:element name="fe-ultimo-evento">
						<xsl:value-of select="@FEULTIMOEVENTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FESIGUIENTEENVIO">
					<xsl:element name="fe-siguiente-envio">
						<xsl:value-of select="@FESIGUIENTEENVIO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMFRECUENCIA">
					<xsl:element name="nm-frecuencia">
						<xsl:value-of select="@NMFRECUENCIA" />
					</xsl:element>
				</xsl:if>
				
				<xsl:if test="@CDTEMPORALIDAD">
					<xsl:element name="cd-temporalidad">
						<xsl:value-of select="@CDTEMPORALIDAD" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FEVENCIMIENTO">
					<xsl:element name="fe-vencimiento">
						<xsl:value-of select="@FEVENCIMIENTO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSCORREO">
					<xsl:element name="ds-correo">
						<xsl:value-of select="@DSCORREO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSMENSAJE">
					<xsl:element name="ds-mensaje">
						<xsl:value-of select="@DSMENSAJE" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FGMANDARPANTALLA">
					<xsl:element name="fg-manda-pantalla">
						<xsl:value-of select="@FGMANDARPANTALLA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@FGPERMANCEPANTALLA">
					<xsl:element name="fg-perm-pantalla">
						<xsl:value-of select="@FGPERMANCEPANTALLA" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTEMPORALIDAD">
					<xsl:element name="ds-temporalidad">
						<xsl:value-of select="@DSTEMPORALIDAD" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSUSUARIO">
					<xsl:element name="ds-usuario">
						<xsl:value-of select="@DSUSUARIO" />
					</xsl:element>
				</xsl:if>

			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>