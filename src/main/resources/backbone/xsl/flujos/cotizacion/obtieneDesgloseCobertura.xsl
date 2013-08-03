<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="row">
        <resultado-cotizacion-vO xsi:type="java:mx.com.aon.flujos.cotizacion.model.ResultadoCotizacionVO">
		<xsl:if test="@CDIDENTIFICA">
			<xsl:element name="cd-identifica">
				<xsl:value-of select="@CDIDENTIFICA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDUNIECO">
			<xsl:element name="cd-unieco">
				<xsl:value-of select="@CDUNIECO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDRAMO">
			<xsl:element name="cd-ramo">
				<xsl:value-of select="@CDRAMO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@ESTADO">
			<xsl:element name="estado">
				<xsl:value-of select="@ESTADO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMPOLIZA">
			<xsl:element name="nm-poliza">
				<xsl:value-of select="@NMPOLIZA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMSUPLEM">
			<xsl:element name="nm-suplem">
				<xsl:value-of select="@NMSUPLEM"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMSITUAC">
			<xsl:element name="n-m-situac">
				<xsl:value-of select="@NMSITUAC"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@STATUS">
			<xsl:element name="status">
				<xsl:value-of select="@STATUS"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDPLAN">
			<xsl:element name="cd-plan">
				<xsl:value-of select="@CDPLAN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSPLAN">
			<xsl:element name="ds-plan">
				<xsl:value-of select="@DSPLAN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDCIAASEG">
			<xsl:element name="cd-ciaaseg">
				<xsl:value-of select="@CDCIAASEG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSUNIECO">
			<xsl:element name="ds-unieco">
				<xsl:value-of select="@DSUNIECO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDPERPAG">
			<xsl:element name="cd-perpag">
				<xsl:value-of select="@CDPERPAG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSPERPAG">
			<xsl:element name="ds-perpag">
				<xsl:value-of select="@DSPERPAG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDTIPSIT">
			<xsl:element name="cd-tipsit">
				<xsl:value-of select="@CDTIPSIT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPO">
			<xsl:element name="ds-tipsit">
				<xsl:value-of select="@DSTIPO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDGARANT">
			<xsl:element name="cd-garant">
				<xsl:value-of select="@CDGARANT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSGARANT">
			<xsl:element name="ds-garant">
				<xsl:value-of select="@DSGARANT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@SWOBLIG">
			<xsl:element name="sw-oblig">
				<xsl:value-of select="@SWOBLIG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMSUMAASEG">
			<xsl:element name="suma-aseg">
				<xsl:value-of select="@NMSUMAASEG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NMIMPFPG">
			<xsl:element name="prima-formap">
				<xsl:value-of select="@NMIMPFPG"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@MNPRIMA">
			<xsl:element name="mn-prima">
				<xsl:value-of select="@MNPRIMA"/>
			</xsl:element>
		</xsl:if>
		
        </resultado-cotizacion-vO>
    </xsl:template>
</xsl:stylesheet>        