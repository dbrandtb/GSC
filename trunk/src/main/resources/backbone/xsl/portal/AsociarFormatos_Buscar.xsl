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
				xsi:type="java:mx.com.aon.portal.model.AsociarFormatoVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDASOCIA">
                    <xsl:element name="cd-asocia">
                        <xsl:value-of select="@CDASOCIA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFORORD">
                    <xsl:element name="cd-forord">
                        <xsl:value-of select="@CDFORORD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFORMATOORDEN">
                    <xsl:element name="ds-formato-orden">
                        <xsl:value-of select="@DSFORMATOORDEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPROCES">
                    <xsl:element name="cd-proces">
                        <xsl:value-of select="@CDPROCES" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPROCESO">
                    <xsl:element name="ds-proceso">
                        <xsl:value-of select="@DSPROCESO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDELEMENTO">
                    <xsl:element name="cd-elemento">
                        <xsl:value-of select="@CDELEMENTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSELEMEN">
                    <xsl:element name="ds-elemen">
                        <xsl:value-of select="@DSELEMEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="ds-unieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="ds-ramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFOLIOAON">
                    <xsl:element name="cd-folio-aon">
                        <xsl:value-of select="@CDFOLIOAON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFOLIOAON">
                    <xsl:element name="ds-folio-aon">
                        <xsl:value-of select="@DSFOLIOAON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFOLIOCIA">
                    <xsl:element name="cd-folio-cia">
                        <xsl:value-of select="@CDFOLIOCIA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFOLIOCIA">
                    <xsl:element name="ds-folio-cia">
                        <xsl:value-of select="@DSFOLIOCIA" />
                    </xsl:element>
                </xsl:if>
 		 </item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>

