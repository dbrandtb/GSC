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
        <rol-vO xsi:type="java:mx.com.aon.portal.model.principal.RolVO">
		<xsl:if test="@CDCONFIGURA">
			<xsl:element name="cd-configura">
				<xsl:value-of select="@CDCONFIGURA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDROL">
			<xsl:element name="cd-rol">
				<xsl:value-of select="@CDROL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSROL">
			<xsl:element name="ds-rol">
				<xsl:value-of select="@DSROL"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDELEMENTO">
			<xsl:element name="cd-elemento">
				<xsl:value-of select="@CDELEMENTO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSELEMEN">
			<xsl:element name="ds-elemen">
				<xsl:value-of select="@DSELEMEN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@CDSECCION">
			<xsl:element name="cd-seccion">
				<xsl:value-of select="@CDSECCION"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSSECCION">
			<xsl:element name="ds-seccion">
				<xsl:value-of select="@DSSECCION"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSTIPO">
				<xsl:element name="ds-tipo">
					<xsl:value-of select="@DSTIPO"/>
				</xsl:element>
			</xsl:if>
		<xsl:if test="@SWHABILITADO">
			<xsl:element name="sw-habilitado">
				<xsl:value-of select="@SWHABILITADO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSARCHIVO">
			<xsl:element name="ds-archivo">
				<xsl:value-of select="@DSARCHIVO"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSCONFIGURA">
			<xsl:element name="ds-configura">
				<xsl:value-of select="@DSCONFIGURA"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@DSESPECIFICACION">
			<xsl:element name="especificacion">
				<xsl:value-of select="@DSESPECIFICACION"/>
			</xsl:element>
		</xsl:if>
        </rol-vO>
    </xsl:template>
</xsl:stylesheet>        