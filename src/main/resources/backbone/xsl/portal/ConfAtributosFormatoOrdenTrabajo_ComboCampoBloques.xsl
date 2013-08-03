<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.ComboBloqueVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:if test="@CDCAMPO">
                <xsl:element name="cd-campo">
                    <xsl:value-of select="@CDCAMPO"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DSCAMPO">
                <xsl:element name="ds-campo">
                    <xsl:value-of select="@DSCAMPO"/>
                </xsl:element>
            </xsl:if>
            
              <xsl:if test="@TIPO">
                <xsl:element name="tipo">
                    <xsl:value-of select="@TIPO"/>
                </xsl:element>
            </xsl:if>
           
              <xsl:if test="@NMMIN">
                <xsl:element name="nm-min">
                    <xsl:value-of select="@NMMIN"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@NMMAX">
                <xsl:element name="nm-max">
                    <xsl:value-of select="@NMMAX"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@NMCAMPO">
                <xsl:element name="nm-campo">
                    <xsl:value-of select="@NMCAMPO"/>
                </xsl:element>
            </xsl:if>
           </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>