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
				<xsl:if test="@DSMENSAJE">
                    <xsl:element name="ds-mensaje">
                        <xsl:value-of select="@DSMENSAJE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFORMATO">
                    <xsl:element name="cd-formato-orden">
                        <xsl:value-of select="@CDFORMATO" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@DSNOMFORMATO">
					<xsl:element name="ds-formato-orden">
						<xsl:value-of select="@DSNOMFORMATO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDMETENV">
					<xsl:element name="cd-met-env">
						<xsl:value-of select="@CDMETENV" />
					</xsl:element>
				</xsl:if>				              
                <xsl:if test="@DSMETENV">
                    <xsl:element name="ds-met-env">
                        <xsl:value-of select="@DSMETENV" />
                    </xsl:element>
                </xsl:if>				
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>