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
            <item-list xsi:type="java:mx.com.aon.portal.model.ConfiguracionAlertasAutomaticoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            
            <xsl:if test="@CDIDUNICO">
                <xsl:element name="cd-id-unico">
                    <xsl:value-of select="@CDIDUNICO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDCLIENTE">
                <xsl:element name="cd-cliente">
                    <xsl:value-of select="@CDCLIENTE"/>
                </xsl:element>
            </xsl:if>  
            
            <xsl:if test="@DSNOMBRE">
                <xsl:element name="ds-nombre">
                    <xsl:value-of select="@DSNOMBRE"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDPROCESO">
                <xsl:element name="cd-proceso">
                    <xsl:value-of select="@CDPROCESO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSPROCESO">
                <xsl:element name="ds-proceso">
                    <xsl:value-of select="@DSPROCESO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSUSUARIO">
                <xsl:element name="ds-usuario">
                    <xsl:value-of select="@DSUSUARIO"/>
                </xsl:element>
            </xsl:if>
                 <xsl:if test="@CDTIPRAM">
                <xsl:element name="cd-tip-ram">
                    <xsl:value-of select="@CDTIPRAM"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSRAMO">
                <xsl:element name="ds-ramo">
                    <xsl:value-of select="@DSRAMO"/>
                </xsl:element>
            </xsl:if>
             <xsl:if test="@CDUNIECO">
                <xsl:element name="cd-uni-eco">
                    <xsl:value-of select="@CDUNIECO"/>
                </xsl:element>
            </xsl:if>
             <xsl:if test="@DSUNIECO">
                <xsl:element name="ds-uni-eco">
                    <xsl:value-of select="@DSUNIECO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDPRODUCTO">
                <xsl:element name="cd-producto">
                    <xsl:value-of select="@CDPRODUCTO"/>
                </xsl:element>
            </xsl:if>
             <xsl:if test="@DSPRODUCTO">
                <xsl:element name="ds-producto">
                    <xsl:value-of select="@DSPRODUCTO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDROL">
                <xsl:element name="cd-rol">
                    <xsl:value-of select="@CDROL"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSROL">
                <xsl:element name="ds-rol">
                    <xsl:value-of select="@DSROL"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSALERTA">
                <xsl:element name="ds-alerta">
                    <xsl:value-of select="@DSALERTA"/>
                </xsl:element>
            </xsl:if>
             
             
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>