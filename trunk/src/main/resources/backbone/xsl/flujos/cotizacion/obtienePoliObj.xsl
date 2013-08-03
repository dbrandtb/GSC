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
        <list-objeto-cotizacion xsi:type="java:mx.com.aon.flujos.cotizacion.model.ObjetoCotizacionVO">
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
			<xsl:if test="@NMSITUAC">
				<xsl:element name="nm-situac">
					<xsl:value-of select="@NMSITUAC"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDTIPOBJ">
				<xsl:element name="cd-tipobj">
					<xsl:value-of select="@CDTIPOBJ"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@NMSUPLEM">
				<xsl:element name="nm-suplem">
					<xsl:value-of select="@NMSUPLEM"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@STATUS">
				<xsl:element name="status">
					<xsl:value-of select="@STATUS"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDGARANT">
				<xsl:element name="cd-garant">
					<xsl:value-of select="@CDGARANT"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDTIPCON">
				<xsl:element name="cd-tipcon">
					<xsl:value-of select="@CDTIPCON"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDCONTAR">
				<xsl:element name="cd-contar">
					<xsl:value-of select="@CDCONTAR"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@NMIMPORT">
				<xsl:element name="nm-import">
					<xsl:value-of select="@NMIMPORT"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@ORDEN">
				<xsl:element name="orden">
					<xsl:value-of select="@ORDEN"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSGARANT">
				<xsl:element name="ds-garant">
					<xsl:value-of select="@DSGARANT"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSTIPCON">
				<xsl:element name="ds-tipcon">
					<xsl:value-of select="@DSTIPCON"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSCONTAR">
				<xsl:element name="ds-contar">
					<xsl:value-of select="@DSCONTAR"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@NMOBJETO">
				<xsl:element name="nm-objeto">
					<xsl:value-of select="@NMOBJETO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSOBJETO">
				<xsl:element name="ds-objeto">
					<xsl:value-of select="@DSOBJETO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@PTOBJETO">
				<xsl:element name="pt-objeto">
					<xsl:value-of select="@PTOBJETO"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@CDAGRUPA">
				<xsl:element name="cd-agrupa">
					<xsl:value-of select="@CDAGRUPA"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@NMVALOR">
				<xsl:element name="nm-valor">
					<xsl:value-of select="@NMVALOR"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@DSDESCRIPCION">
					<xsl:element name="ds-descripcion">
			    		<xsl:value-of select="@DSDESCRIPCION"/>
			   		</xsl:element>
			</xsl:if>
        </list-objeto-cotizacion>
    </xsl:template>
</xsl:stylesheet>        