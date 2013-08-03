<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <!--<xsl:choose>
                   <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S'">-->
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
            <!--</xsl:when>
                   <xsl:otherwise>
                       <xsl:call-template name="error"/>
                   </xsl:otherwise>
               </xsl:choose>-->
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@FISJUR">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            FISJUR
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@FISJUR"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSFISJUR">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSFISJUR
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSFISJUR"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@SEXO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            SEXO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@SEXO"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSSEXO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSSEXO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSSEXO"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@FENACIMI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            FENACIMI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@FENACIMI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@APELLIDO1">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            APELLIDO1
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@APELLIDO1"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@APELLIDO2">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            APELLIDO2
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@APELLIDO2"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NOMBRE1">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NOMBRE1
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NOMBRE1"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NOMBRE2">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NOMBRE2
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NOMBRE2"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSNOMBRE
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSNOMBRE"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDPERSON
                        </key>
                        <value xsi:type="java:java.lang.Long">
                            <xsl:value-of select="@CDPERSON"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDTIPPER">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDTIPPER
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDTIPPER"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NMORDDOM">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMORDDOM
                        </key>
                        <value xsi:type="java:java.lang.Integer">
                            <xsl:value-of select="@NMORDDOM"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDTIPDOM">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDTIPDOM
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDTIPDOM"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSTIPDOM">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSTIPDOM
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSTIPDOM"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDSIGLAS">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDSIGLAS
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDSIGLAS"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSSIGLAS">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSSIGLAS
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSSIGLAS"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDIDIOMA">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDIDIOMA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDIDIOMA"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSIDIOMA">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSIDIOMA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSIDIOMA"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NMTELEFO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMTELEFO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NMTELEFO"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSDOMICI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSDOMICI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSDOMICI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NMNUMERO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMNUMERO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NMNUMERO"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDPOSTAL">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDPOSTAL
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDPOSTAL"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDPAIS">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDPAIS
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDPAIS"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSPAIS">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSPAIS
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSPAIS"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDPROVIN">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDPROVIN
                        </key>
                        <value xsi:type="java:java.lang.Integer">
                            <xsl:value-of select="@CDPROVIN"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSPROVIN">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSPROVIN
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSPROVIN"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDMUNICI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDMUNICI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDMUNICI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSMUNICI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSMUNICI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSMUNICI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDCIUDAD">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDCIUDAD
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDCIUDAD"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSCIUDAD">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSCIUDAD
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSCIUDAD"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDCOLONI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDCOLONI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDCOLONI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@DSCOLONI">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DSCOLONI
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DSCOLONI"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NMTELEX">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMTELEX
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NMTELEX"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@NMFAX">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMFAX
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NMFAX"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@OTPISO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            OTPISO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@OTPISO"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDTIPIDE">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDTIPIDE
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDTIPIDE"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@CDIDEPER">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDIDEPER
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDIDEPER"/>
                        </value>
                    </values>
                </xsl:if>
            </cursor>
        </xsl:for-each>
    </xsl:template>
    <xsl:template name="error">
        <xsl:element name="cd-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_CDERROR']/@value"/>
        </xsl:element>
        <xsl:element name="ds-error">
            <xsl:value-of select="result/storedProcedure/outparam[@id='po_DSERROR']/@value"/>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
