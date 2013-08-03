<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
            <item-list xsi:type="java:mx.com.aon.configurador.pantallas.model.ConjuntoPantallaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                
                <xsl:if test="@CDCONJUNTO">
                <xsl:element name="cd-conjunto">
                    <xsl:value-of select="@CDCONJUNTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSNOMBRECONJUNTO">
                <xsl:element name="nombre-conjunto">
                    <xsl:value-of select="@DSNOMBRECONJUNTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSDESCCONJUNTO">
                <xsl:element name="descripcion">
                    <xsl:value-of select="@DSDESCCONJUNTO"/>
                </xsl:element>
            </xsl:if>
            
            
            <xsl:if test="@CDPROCESO">
                <xsl:element name="cd-proceso">
                    <xsl:value-of select="@CDPROCESO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSPROCESO">
                <xsl:element name="proceso">
                    <xsl:value-of select="@DSPROCESO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CDELEMENTO">
                <xsl:element name="cd-cliente">
                    <xsl:value-of select="@CDELEMENTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSELEMEN">
                <xsl:element name="cliente">
                    <xsl:value-of select="@DSELEMEN"/>
                </xsl:element>
            </xsl:if>

            <xsl:if test="@CDRAMO">
                <xsl:element name="cd-ramo">
                    <xsl:value-of select="@CDRAMO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSRAMO">
                <xsl:element name="ds-ramo">
                    <xsl:value-of select="@DSRAMO"/>
                </xsl:element>
            </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>        