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
				xsi:type="java:mx.com.aon.portal.model.AtribuVblesTipoObjVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

				<xsl:if test="@CDTIPOBJ">
                    <xsl:element name="cdtipobj">
                        <xsl:value-of select="@CDTIPOBJ" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDATRIBU">
                    <xsl:element name="cdatribu">
                        <xsl:value-of select="@CDATRIBU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWFORMAT">
                    <xsl:element name="swformat">
                        <xsl:value-of select="@SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMLMAX">
                    <xsl:element name="nmlmax">
                        <xsl:value-of select="@NMLMAX" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMLMIN">
                    <xsl:element name="nmlmin">
                        <xsl:value-of select="@NMLMIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWOBLIGA">
                    <xsl:element name="swobliga">
                        <xsl:value-of select="@SWOBLIGA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSATRIBU">
                    <xsl:element name="dsatribu">
                        <xsl:value-of select="@DSATRIBU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTABVAL">
                    <xsl:element name="ottabval">
                        <xsl:value-of select="@OTTABVAL" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@DSTABLA">
                    <xsl:element name="dstabla">
                        <xsl:value-of select="@DSTABLA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWPRODUC">
                    <xsl:element name="swproduc">
                        <xsl:value-of select="@SWPRODUC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWSUPLEM">
                    <xsl:element name="swsuplem">
                        <xsl:value-of select="@SWSUPLEM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWACTUAL">
                    <xsl:element name="swactual">
                        <xsl:value-of select="@SWACTUAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@GB_SWFORMAT">
                    <xsl:element name="gb_swformat">
                        <xsl:value-of select="@GB_SWFORMAT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDATRIBU_PADRE">
                    <xsl:element name="cdatribu_padre">
                        <xsl:value-of select="@CDATRIBU_PADRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMORDEN">
                    <xsl:element name="nmorden">
                        <xsl:value-of select="@NMORDEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMAGRUPA">
                    <xsl:element name="nmagrupa">
                        <xsl:value-of select="@NMAGRUPA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCONDICVIS">
                    <xsl:element name="cdcondicvis">
                        <xsl:value-of select="@CDCONDICVIS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSATRIBU_PADRE">
                    <xsl:element name="dsatribu_padre">
                        <xsl:value-of select="@DSATRIBU_PADRE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSCONDICVIS">
                    <xsl:element name="dscondicvis">
                        <xsl:value-of select="@DSCONDICVIS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWCOTIZA">
                    <xsl:element name="swcotiza">
                        <xsl:value-of select="@SWCOTIZA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWDATCOM">
                    <xsl:element name="swdatcom">
                        <xsl:value-of select="@SWDATCOM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWCOMOBL">
                    <xsl:element name="swcomobl">
                        <xsl:value-of select="@SWCOMOBL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWCOMUPD">
                    <xsl:element name="swcomupd">
                        <xsl:value-of select="@SWCOMUPD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWENDOSO">
                    <xsl:element name="swendoso">
                        <xsl:value-of select="@SWENDOSO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWENDOBL">
                    <xsl:element name="swendobl">
                        <xsl:value-of select="@SWENDOBL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRET">
                    <xsl:element name="dsnombret">
                        <xsl:value-of select="@DSNOMBRET" />
                    </xsl:element>
                </xsl:if>                
                             
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>