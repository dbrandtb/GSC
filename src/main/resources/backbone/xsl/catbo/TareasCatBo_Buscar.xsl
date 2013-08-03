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
			<item-list xsi:type="java:mx.com.aon.catbo.model.TareaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
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
                <xsl:if test="@CDMODULO">
                    <xsl:element name="cd-modulo">
                        <xsl:value-of select="@CDMODULO" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@DSMODULO">
					<xsl:element name="ds-modulo">
						<xsl:value-of select="@DSMODULO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@CDPRIORD">
					<xsl:element name="cd-priord">
						<xsl:value-of select="@CDPRIORD" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@DSPRIORD">
                    <xsl:element name="ds-priord">
                        <xsl:value-of select="@DSPRIORD" />
                    </xsl:element>
                </xsl:if>
                
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>