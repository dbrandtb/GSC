<?xml version="1.0" encoding="UTF-8"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows "/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
		<tree-vO xsi:type="java:mx.com.aon.catweb.configuracion.producto.expresiones.model.RamaVO">
			<!--Strings-->
			<xsl:if test="@*[position()=4]">
				<xsl:element name="codigo-objeto">
					<xsl:value-of select="@*[position()=4]"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="@*[position()=5]">
				<xsl:element name="text">
					<xsl:value-of select="@*[position()=5]"/>
				</xsl:element>
			</xsl:if>
		</tree-vO>
	</xsl:template>
</xsl:stylesheet>