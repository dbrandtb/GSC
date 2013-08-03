<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:element name="msg-id">
				<xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
			</xsl:element>
			<xsl:element name="msg">
				<xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
			</xsl:element>

			<xsl:apply-templates select="result/storedProcedure/outparam/rows" />

		</wrapper-resultados>
	</xsl:template>


	<xsl:template match="rows">
		<xsl:for-each select="row">
			<item-list
				xsi:type="java:mx.com.aon.catbo.model.NotificacionVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDNOTIFICACION">
					<xsl:element name="cd-notificacion">
						<xsl:value-of select="@CDNOTIFICACION" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSNOTIFICACION">
					<xsl:element name="ds-notificacion">
						<xsl:value-of select="@DSNOTIFICACION" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@CDREGION">
                    <xsl:element name="cd-region">
                        <xsl:value-of select="@CDREGION" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSREGION">
                    <xsl:element name="ds-region">
                        <xsl:value-of select="@DSREGION" />
                    </xsl:element>
                </xsl:if>
				
				<xsl:if test="@CDPROCESO">
                    <xsl:element name="cd-proceso">
                        <xsl:value-of select="@CDPROCESO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPROCESO">
                    <xsl:element name="ds-proceso">
                        <xsl:value-of select="@DSPROCESO" />
                    </xsl:element>
                </xsl:if>
				
				<xsl:if test="@CDSTATUS">
                    <xsl:element name="cd-estado">
                        <xsl:value-of select="@CDSTATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSSTATUS">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSSTATUS" />
                    </xsl:element>
                </xsl:if>
				
                <xsl:if test="@CODIGO">
                    <xsl:element name="cd-met-env">
                        <xsl:value-of select="@CODIGO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DESCRIPC">
                    <xsl:element name="ds-met-env">
                        <xsl:value-of select="@DESCRIPC" />
                    </xsl:element>
                </xsl:if>
				
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>