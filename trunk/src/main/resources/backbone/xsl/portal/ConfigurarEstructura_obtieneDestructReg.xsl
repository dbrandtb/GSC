<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
    xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.ConfigurarEstructuraVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:if test="@CDESTRUC">
                <xsl:element name="codigo-estructura">
                    <xsl:value-of select="@CDESTRUC" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDELEMENTO">
                <xsl:element name="codigo-elemento">
                    <xsl:value-of select="@CDELEMENTO" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSELEMEN">
                <xsl:element name="nombre">
                    <xsl:value-of select="@DSELEMEN" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDPADRE">
                <xsl:element name="vinculo-padre">
                    <xsl:value-of select="@CDPADRE" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSPADRE">
                <xsl:element name="ds-padre">
                    <xsl:value-of select="@DSPADRE" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDTIPNIV">
                <xsl:element name="codigo-tipo-nivel">
                    <xsl:value-of select="@CDTIPNIV" />
                </xsl:element>
            </xsl:if>            
            <xsl:if test="@NIVEL">
                <xsl:element name="tipo-nivel">
                    <xsl:value-of select="@NIVEL" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@NMPOSIC">
                <xsl:element name="posicion">
                    <xsl:value-of select="@NMPOSIC" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@SWNOMINA">
                <xsl:element name="nomina">
                    <xsl:value-of select="@SWNOMINA" />
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDPERSON">
                <xsl:element name="codigo-persona">
                    <xsl:value-of select="@CDPERSON" />
                </xsl:element>
            </xsl:if>                     
            <xsl:if test="@DSNOMBRE">
                <xsl:element name="ds-nombre">
                    <xsl:value-of select="@DSNOMBRE" />
                </xsl:element>
            </xsl:if>    
           </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>