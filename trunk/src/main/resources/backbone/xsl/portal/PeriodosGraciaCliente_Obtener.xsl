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
            <item-list xsi:type="java:mx.com.aon.portal.model.PeriodoGraciaClienteVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:if test="@CDELEMENTO">
                <xsl:element name="cd-elemento">
                    <xsl:value-of select="@CDELEMENTO"/>
                </xsl:element>
            </xsl:if>
            
            
            <xsl:if test="@CLIENTE">
                <xsl:element name="cliente">
                    <xsl:value-of select="@CLIENTE"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@CVEASEGURADORA">
                <xsl:element name="cve-aseguradora">
                    <xsl:value-of select="@CVEASEGURADORA"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@ASEGURADORA">
                <xsl:element name="aseguradora">
                    <xsl:value-of select="@ASEGURADORA"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CVEPRODUCTO">
                <xsl:element name="cve-producto">
                    <xsl:value-of select="@CVEPRODUCTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@PRODUCTO">
                <xsl:element name="producto">
                    <xsl:value-of select="@PRODUCTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@RECIBOS">
                <xsl:element name="recibos">
                    <xsl:value-of select="@RECIBOS"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DIASDEGRACIA">
                <xsl:element name="dias-de-gracia">
                    <xsl:value-of select="@DIASDEGRACIA"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DIASANTESDECANCELACION">
                <xsl:element name="dias-antes-de-cancelacion">
                    <xsl:value-of select="@DIASANTESDECANCELACION"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CDTRAMO">
                <xsl:element name="cd-tramo">
                    <xsl:value-of select="@CDTRAMO"/>
                </xsl:element>
            </xsl:if>
          
          </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>