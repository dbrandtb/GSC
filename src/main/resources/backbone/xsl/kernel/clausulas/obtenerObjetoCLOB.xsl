<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- edited with XML Spy v4.3 U (http://www.xmlspy.com) by a (a) -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
   
    <xsl:template match="/">
        <data-type-vO xsi:type="java:mx.com.royalsun.vo.DataTypeVO">
            <xsl:apply-templates select="result/rows/row" />
        </data-type-vO>
    </xsl:template>
    
    <xsl:template match="row">
        
            <xsl:if test="@DSLINEA">
                <xsl:element name="string-value">
                    <xsl:value-of select="@DSLINEA" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@INT_VAL">
                <xsl:element name="int-value">
                    <xsl:value-of select="@INT_VAL" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@LONG_VAL">
                <xsl:element name="long-value">
                    <xsl:value-of select="@LONG_VAL" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@FLOAT_VAL">
                <xsl:element name="float-value">
                    <xsl:value-of select="@FLOAT_VAL" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DOUBLE_VAL">
                <xsl:element name="double-value">
                    <xsl:value-of select="@DOUBLE_VAL" />
                </xsl:element>
            </xsl:if>
        
    </xsl:template>
</xsl:stylesheet>
