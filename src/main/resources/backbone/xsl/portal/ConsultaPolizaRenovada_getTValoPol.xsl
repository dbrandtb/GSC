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
				xsi:type="java:mx.com.aon.portal.model.ValorPolizaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

				<xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmSuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWFORMAT">
                    <xsl:element name="swFormat">
                        <xsl:value-of select="@SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWOBLIGA">
                    <xsl:element name="swObliga">
                        <xsl:value-of select="@SWOBLIGA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMLMAX">
                    <xsl:element name="nmLMax">
                        <xsl:value-of select="@NMLMAX" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMLMIN">
                    <xsl:element name="nmLMin">
                        <xsl:value-of select="@NMLMIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTABVAL">
                    <xsl:element name="otTabVal">
                        <xsl:value-of select="@OTTABVAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWPRODUC">
                    <xsl:element name="swProduc">
                        <xsl:value-of select="@SWPRODUC" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@SWSUPLEM">
                    <xsl:element name="swSuplem">
                        <xsl:value-of select="@SWSUPLEM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@GB_SWFORMAT">
                    <xsl:element name="gb_SwFormat">
                        <xsl:value-of select="@GB_SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDATRIBU">
                    <xsl:element name="cdAtribu">
                        <xsl:value-of select="@CDATRIBU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSATRIBU">
                    <xsl:element name="dsAtribu">
                        <xsl:value-of select="@DSATRIBU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTVALOR">
                    <xsl:element name="otValor">
                        <xsl:value-of select="@OTVALOR" />
                    </xsl:element>
                </xsl:if>                
                             
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>