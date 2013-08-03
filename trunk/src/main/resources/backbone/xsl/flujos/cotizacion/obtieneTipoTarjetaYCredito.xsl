<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <!-- La posicion uno siempre tendra que ser la clave -->
                <xsl:if test="@*[position()=1]">
                    <xsl:element name="msg-id">
                        <xsl:value-of select="@*[position()=1]"/>
                    </xsl:element>
                </xsl:if>
                <!-- La posicion dos siempre tendra que ser la descripcion -->
                <xsl:if test="@*[position()=2]">
                    <xsl:element name="msg-title">
                        <xsl:value-of select="@*[position()=2]"/>
                    </xsl:element>
                </xsl:if>
                <!-- La posicion tres tendra SWCREDITO -->
                <xsl:if test="@SWCREDITO">
                    <xsl:element name="resultado">
                        <xsl:value-of select="@SWCREDITO" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>