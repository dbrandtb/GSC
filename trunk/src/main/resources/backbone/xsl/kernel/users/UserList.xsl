<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
  <xsl:template match="/">
    <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
      <xsl:apply-templates select="result/rows"/>
    </array-list>
  </xsl:template>
  <xsl:template match="rows">
    <xsl:for-each select="row">
      <xsl:apply-templates select="."/>   
    </xsl:for-each>
  </xsl:template>
  <xsl:template match="row">
    <user xsi:type="java:mx.com.royalsun.vo.User">
      <xsl:if test="@ID">
        <xsl:element name="id">
          <xsl:value-of select="@ID"/>
        </xsl:element>
      </xsl:if>
      <xsl:if test="@FIRST_NAME">
        <xsl:element name="first-name">
          <xsl:value-of select="@FIRST_NAME"/>
        </xsl:element>
      </xsl:if>
      <xsl:if test="@FIRST_NAME">
        <xsl:element name="last-name">
          <xsl:value-of select="@FIRST_NAME"/>
        </xsl:element>
      </xsl:if>
      <xsl:if test="@BIRTHDAY">
        <xsl:element name="birthday">
          <xsl:value-of select="@BIRTHDAY"/>
        </xsl:element>
      </xsl:if>
    </user>
  </xsl:template>
</xsl:stylesheet>