<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

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

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@DSMENU">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSMENU" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSELEMEN">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSELEMEN" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSSISROL">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSSISROL" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSUSUARI">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSUSUARI" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSTIPOMENI">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSTIPOMENI" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSESTADO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSESTADO" />
                    </string>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
