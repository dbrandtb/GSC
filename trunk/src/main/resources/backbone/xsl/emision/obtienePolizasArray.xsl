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
                <!--  
                    <xsl:if test="@CDUNIECO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@CDUNIECO"/>
                    </string>
                    </xsl:if>
                    <xsl:if test="@CDRAMO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@CDRAMO"/>
                    </string>
                    </xsl:if>
                    <xsl:if test="@ESTADO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@ESTADO"/>
                    </string>
                    </xsl:if>
                    <xsl:if test="@NMPOLIZA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@NMPOLIZA"/>
                    </string>
                    </xsl:if>
                -->
                <xsl:if test="@DSUNIECO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSUNIECO" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSRAMO" />
                    </string>
                </xsl:if>
                <xsl:if test="@NMPOLIEX">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@NMPOLIEX" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSPERPAG">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSPERPAG" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSNOMBRE" />
                    </string>
                </xsl:if>
                <xsl:if test="@RFC">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@RFC" />
                    </string>
                </xsl:if>
                <xsl:if test="@VIGENCIA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@VIGENCIA" />
                    </string>
                </xsl:if>
                <xsl:if test="@DSFORPAG">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@DSFORPAG" />
                    </string>
                </xsl:if>

                <xsl:if test="@PRIMA">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@PRIMA" />
                    </string>
                </xsl:if>

                <xsl:if test="@INCISO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                        <xsl:value-of select="@INCISO" />
                    </string>
                </xsl:if>
                <!--
                    <xsl:if test="@FEEFECTO">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@FEEFECTO"/>
                    </string>
                    </xsl:if>
                    
                    <xsl:if test="@FEVENCIM">
                    <string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                    <xsl:value-of select="@FEVENCIM"/>
                    </string>
                    </xsl:if>                
                -->


            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>