<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
  <xsl:template match="/">
    <user xsi:type="java:mx.com.aon.portal.model.UserVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
      <xsl:apply-templates select="result/rows/row"/>
    </user>
  </xsl:template>
  <xsl:template match="row">
    <xsl:apply-templates select="."/>
  </xsl:template>
  <xsl:template match="row">
	  <xsl:if test="@NAME">
	    <xsl:element name="name">
	      <xsl:value-of select="@NAME"/>
	    </xsl:element>
	  </xsl:if>
	  <xsl:if test="@LASTNAME">
	    <xsl:element name="last-name">
	      <xsl:value-of select="@LASTNAME"/>
	    </xsl:element>
	  </xsl:if>
	  <xsl:if test="@PERFIL">
	    <xsl:element name="perfil">
	      <xsl:value-of select="@PERFIL"/>
	    </xsl:element>
	  </xsl:if>
  </xsl:template>
</xsl:stylesheet>