<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="/">
		<array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
		</array-list>
	</xsl:template>
	<xsl:template match="rows">
		<xsl:for-each select="row">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="row">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:if test="@CDCONJUNTO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@CDCONJUNTO"/>
				</string>
            </xsl:if>
            <xsl:if test="@DSNOMBRECONJUNTO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSNOMBRECONJUNTO"/>
				</string>
            </xsl:if>
            <xsl:if test="@DSDESCCONJUNTO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSDESCCONJUNTO"/>
				</string>
            </xsl:if>
            <xsl:if test="@CDPROCESO">
            	<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@CDPROCESO"/>
				</string>
            </xsl:if>
            <xsl:if test="@DSPROCESO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSPROCESO"/>
				</string>
            </xsl:if>
            
            <xsl:if test="@CDELEMENTO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@CDELEMENTO"/>
				</string>
            </xsl:if>
            <xsl:if test="@DSELEMEN">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSELEMEN"/>
				</string>
            </xsl:if>
            <xsl:if test="@CDRAMO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@CDRAMO"/>
				</string>
            </xsl:if>
            <xsl:if test="@DSRAMO">
                <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:value-of select="@DSRAMO"/>
				</string>
            </xsl:if>
		</array-list>
    </xsl:template>
</xsl:stylesheet>