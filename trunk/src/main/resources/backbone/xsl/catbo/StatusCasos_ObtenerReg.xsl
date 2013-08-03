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
			<item-list xsi:type="java:mx.com.aon.catbo.model.StatusCasoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDSTATUS">
					<xsl:element name="cd-status">
						<xsl:value-of select="@CDSTATUS" />
					</xsl:element>
				</xsl:if>
				<xsl:if test="@DSSTATUS">
					<xsl:element name="ds-status">
						<xsl:value-of select="@DSSTATUS" />
					</xsl:element>
				</xsl:if>
                <xsl:if test="@INDAVISO">
                    <xsl:element name="ind-aviso">
                        <xsl:value-of select="@INDAVISO" />
                    </xsl:element>
                </xsl:if>
                
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>