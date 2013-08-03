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
				xsi:type="java:mx.com.aon.portal.model.PeriodosGraciaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDTRAMO">
					<xsl:element name="cd-tramo">
						<xsl:value-of select="@CDTRAMO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSTRAMO">
					<xsl:element name="ds-tramo">
						<xsl:value-of select="@DSTRAMO" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@NMMINIMO">
					<xsl:element name="nm-minimo">
						<xsl:value-of select="@NMMINIMO" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@NMMAXIMO">
                    <xsl:element name="nm-maximo">
                        <xsl:value-of select="@NMMAXIMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DIASGRAC">
                    <xsl:element name="dias-grac">
                        <xsl:value-of select="@DIASGRAC"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DIASCANC">
                    <xsl:element name="dias-canc">
                        <xsl:value-of select="@DIASCANC"/>
                    </xsl:element>
                </xsl:if>              
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
