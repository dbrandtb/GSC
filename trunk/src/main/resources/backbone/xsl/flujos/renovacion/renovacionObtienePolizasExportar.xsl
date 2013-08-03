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
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@ASEGURADO">
        			<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        				<xsl:value-of select="@ASEGURADO"/>
        			</string>
        		</xsl:if>
                <xsl:if test="@ASEGURADORA">
        			<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        				<xsl:value-of select="@ASEGURADORA"/>
        			</string>
        		</xsl:if>
                <xsl:if test="@PRODUCTO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@PRODUCTO"/>
                    </string>
                </xsl:if>
                <xsl:if test="@POLIZA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@POLIZA"/>
                    </string>
                </xsl:if>
                <xsl:if test="@INCISO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@INCISO"/>
                    </string>
                </xsl:if>
                <xsl:if test="@FECHA_RENOVA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@FECHA_RENOVA"/>
                    </string>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 