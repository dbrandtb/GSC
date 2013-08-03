<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="/">
		<user xsi:type="java:mx.com.aon.portal.model.PerfilVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/rows/row"/>
		</user>
	</xsl:template>
	<xsl:template match="row">
		<xsl:apply-templates select="."/>
	</xsl:template>
	<xsl:template match="row">
		<xsl:if test="@TOP">
			<xsl:element name="top">
				<xsl:value-of select="@TOP"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NAV">
			<xsl:element name="nav">
				<xsl:value-of select="@NAV"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@TOPLEFT">
			<xsl:element name="top-left">
				<xsl:value-of select="@TOPLEFT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@TOPCENTER">
			<xsl:element name="top-center">
				<xsl:value-of select="@TOPCENTER"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@TOPRIGHT">
			<xsl:element name="top-right">
				<xsl:value-of select="@TOPRIGHT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEFT_1">
			<xsl:element name="left_1">
				<xsl:value-of select="@LEFT_1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEFT_2">
			<xsl:element name="left_2">
				<xsl:value-of select="@LEFT_2"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEFT_3">
			<xsl:element name="left_3">
				<xsl:value-of select="@LEFT_3"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEFT_4">
			<xsl:element name="left_4">
				<xsl:value-of select="@LEFT_4"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@LEFT_5">
			<xsl:element name="left_5">
				<xsl:value-of select="@LEFT_5"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@RIGHT_1">
			<xsl:element name="right_1">
				<xsl:value-of select="@RIGHT_1"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@RIGHT_2">
			<xsl:element name="right_2">
				<xsl:value-of select="@RIGHT_2"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@RIGHT_3">
			<xsl:element name="right_3">
				<xsl:value-of select="@RIGHT_3"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@RIGHT_4">
			<xsl:element name="right_4">
				<xsl:value-of select="@RIGHT_4"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@RIGHT_5">
			<xsl:element name="right_5">
				<xsl:value-of select="@RIGHT_5"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@MAIN">
			<xsl:element name="main">
				<xsl:value-of select="@MAIN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@NEWS">
			<xsl:element name="news">
				<xsl:value-of select="@NEWS"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@KNEWTHAT">
			<xsl:element name="knewthat">
				<xsl:value-of select="@KNEWTHAT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@MAINDOWN">
			<xsl:element name="main-down">
				<xsl:value-of select="@MAINDOWN"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTHERLEFT">
			<xsl:element name="other-left">
				<xsl:value-of select="@OTHERLEFT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTHERRIGHT">
			<xsl:element name="other-right">
				<xsl:value-of select="@OTHERRIGHT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@OTHERS">
			<xsl:element name="others">
				<xsl:value-of select="@OTHERS"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FOOTERLEFT">
			<xsl:element name="footer-left">
				<xsl:value-of select="@FOOTERLEFT"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FOOTERCENTER">
			<xsl:element name="footer-center">
				<xsl:value-of select="@FOOTERCENTER"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="@FOOTERRIGHT">
			<xsl:element name="footer-right">
				<xsl:value-of select="@FOOTERRIGHT"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
