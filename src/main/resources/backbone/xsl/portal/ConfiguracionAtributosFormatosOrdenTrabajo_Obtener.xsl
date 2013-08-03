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
            <item-list xsi:type="java:mx.com.aon.portal.model.ConfiguracionAtributoFormatoOrdenTrabajoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:if test="@CDFORMATOORDEN">
                <xsl:element name="cd-formato-orden">
                    <xsl:value-of select="@CDFORMATOORDEN"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSFORMATOORDEN">
                <xsl:element name="ds-formato-orden">
                    <xsl:value-of select="@DSFORMATOORDEN"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDSECCION">
                <xsl:element name="cd-seccion">
                    <xsl:value-of select="@CDSECCION"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSSECCION">
                <xsl:element name="ds-seccion">
                    <xsl:value-of select="@DSSECCION"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDATRIBU">
                <xsl:element name="cd-atribu">
                    <xsl:value-of select="@CDATRIBU"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSATRIBU">
                <xsl:element name="ds-atribu">
                    <xsl:value-of select="@DSATRIBU"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDBLOQUE">
                <xsl:element name="cd-bloque">
                    <xsl:value-of select="@CDBLOQUE"/>
                </xsl:element>
            </xsl:if> 
            <xsl:if test="@DSBLOQUE">
                <xsl:element name="ds-bloque">
                    <xsl:value-of select="@DSBLOQUE"/>
                </xsl:element>
            </xsl:if>   
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
              <xsl:if test="@OTTABVAL">
                <xsl:element name="ot-tab-val">
                    <xsl:value-of select="@OTTABVAL"/>
                </xsl:element>
            </xsl:if>   
            <xsl:if test="@SWFORMAT">
                <xsl:element name="sw-format">
                    <xsl:value-of select="@SWFORMAT"/>
                </xsl:element>
            </xsl:if>   
             <xsl:if test="@NMLMAX">
                <xsl:element name="nml-max">
                    <xsl:value-of select="@NMLMAX"/>
                </xsl:element>
            </xsl:if>   
              <xsl:if test="@NMLMIN">
                <xsl:element name="nml-min">
                    <xsl:value-of select="@NMLMIN"/>
                </xsl:element>
            </xsl:if>         
            <xsl:if test="@CDEXPRES">
                <xsl:element name="cd-expres">
                    <xsl:value-of select="@CDEXPRES"/>
                </xsl:element>
            </xsl:if>   
             <xsl:if test="@NMORDEN">
                <xsl:element name="nm-orden">
                    <xsl:value-of select="@NMORDEN"/>
                </xsl:element>
            </xsl:if>             
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>