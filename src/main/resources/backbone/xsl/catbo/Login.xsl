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
			
			<xsl:element name="resultado">
				<xsl:value-of
					select="result/storedProcedure/outparam[@id='NombreUsuario']/@value" />
			</xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

		</wrapper-resultados>
	</xsl:template>


	<xsl:template match="rows">
		<xsl:for-each select="row">
			<item-list
				xsi:type="java:mx.com.aon.catbo.model.UsuarioVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDUSUARI">
					<xsl:element name="id-usuario">
						<xsl:value-of select="@CDUSUARI" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@DSUSUARI">
                    <xsl:element name="nombre">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTDBA">
                    <xsl:element name="otdba">
                        <xsl:value-of select="@OTDBA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMODELO">
                    <xsl:element name="cd-modelo">
                        <xsl:value-of select="@CDMODELO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMODGRA">
                    <xsl:element name="cdmodgra">
                        <xsl:value-of select="@CDMODGRA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@LANG_CODE">
                    <xsl:element name="lang-code">
                        <xsl:value-of select="@LANG_CODE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@REGION_ID">
                    <xsl:element name="id-region">
                        <xsl:value-of select="@REGION_ID" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWCIAAGE">
                    <xsl:element name="swciaage">
                        <xsl:value-of select="@SWCIAAGE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDIMPRES">
                    <xsl:element name="cdimres">
                        <xsl:value-of select="@CDIMPRES" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWINTEXT">
                    <xsl:element name="swintext">
                        <xsl:value-of select="@SWINTEXT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@COUNTRY_CODE">
                    <xsl:element name="country-code">
                        <xsl:value-of select="@COUNTRY_CODE" />
                    </xsl:element>
                </xsl:if>

			</item-list>
		</xsl:for-each>
	</xsl:template>


</xsl:stylesheet>