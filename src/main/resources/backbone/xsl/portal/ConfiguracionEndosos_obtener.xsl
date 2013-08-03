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
				xsi:type="java:mx.com.aon.portal.model.TipoSuplementoVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				
                <xsl:if test="@CDTIPSUP">
                    <xsl:element name="cd-tip-sup">
                        <xsl:value-of select="@CDTIPSUP" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPSUP">
                    <xsl:element name="ds-tip-sup">
                        <xsl:value-of select="@DSTIPSUP" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWSUPLEM">
                    <xsl:element name="sw-suplem">
                        <xsl:value-of select="@SWSUPLEM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWTARIFI">
                    <xsl:element name="sw-tari-fi">
                        <xsl:value-of select="@SWTARIFI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPDOC">
                    <xsl:element name="cd-tip-doc">
                        <xsl:value-of select="@CDTIPDOC" />
                    </xsl:element>
                </xsl:if>
                
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>