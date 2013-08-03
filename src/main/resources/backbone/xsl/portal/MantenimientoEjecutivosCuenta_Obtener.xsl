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
				xsi:type="java:mx.com.aon.portal.model.EjecutivoCuentaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDAGENTE">
                    <xsl:element name="cd-agente">
                        <xsl:value-of select="@CDAGENTE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="ds-person">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEDESDE">
                    <xsl:element name="fe-inicio">
                        <xsl:value-of select="@FEDESDE" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FEHASTA">
                    <xsl:element name="fe-fin">
                        <xsl:value-of select="@FEHASTA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@SWACTIVO">
                    <xsl:element name="cd-estado">
                        <xsl:value-of select="@SWACTIVO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSESTATUS">
                    <xsl:element name="ds-estado">
                        <xsl:value-of select="@DSESTATUS" />
                    </xsl:element>
                </xsl:if>
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>