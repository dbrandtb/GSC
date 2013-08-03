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
                <!--  
                <xsl:if test="@ASEGURADORA">
                    <xsl:element name="cd-cia">
                        <xsl:value-of select="@ASEGURADORA"/>
                    </xsl:element>
                </xsl:if>
                -->
                <xsl:if test="@DSASEGURADORA">
        			<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        				<xsl:value-of select="@DSASEGURADORA"/>
        			</string>
        		</xsl:if>
                <!--  
                <xsl:if test="@PRODUCTO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@PRODUCTO"/>
                    </xsl:element>
                </xsl:if>
                -->
                <xsl:if test="@DSRAMO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSRAMO"/>
                    </string>
                </xsl:if>
                <!--  
                <xsl:if test="@CLI">
                    <xsl:element name="cd-cliente">
                        <xsl:value-of select="@CLI"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CLIENTE">
                    <xsl:element name="cliente">
                        <xsl:value-of select="@CLIENTE"/>
                    </xsl:element>
                </xsl:if>
                -->
                <xsl:if test="@NMPOLIEX_EXTANT">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@NMPOLIEX_EXTANT"/>
                    </string>
                </xsl:if>
                <xsl:if test="@POLIZA_RENOVACION">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@POLIZA_RENOVACION"/>
                    </string>
                </xsl:if>
                <xsl:if test="@INCISO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@INCISO"/>
                    </string>
                </xsl:if>
                <xsl:if test="@FECHA_RENOVACION">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@FECHA_RENOVACION"/>
                    </string>
                </xsl:if>
                <xsl:if test="@PRIMA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@PRIMA"/>
                    </string>
                </xsl:if>
                <!--  
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
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMANNO">
                    <xsl:element name="nmanno">
                        <xsl:value-of select="@NMANNO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMMES">
                    <xsl:element name="nmmes">
                        <xsl:value-of select="@NMMES"/>
                    </xsl:element>
                </xsl:if>
                -->
                <xsl:if test="@SWAPROBADA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@SWAPROBADA"/>
                    </string>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 