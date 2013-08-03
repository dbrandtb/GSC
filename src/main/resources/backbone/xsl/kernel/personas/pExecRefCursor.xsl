<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
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
                <xsl:if test="@CDPERSON">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            CDPERSON
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@CDPERSON"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@OTFISJUR">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            OTFISJUR
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@OTFISJUR"/>
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
                <xsl:if test="@OTSEXO">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            OTSEXO
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@OTSEXO"/>
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
                <xsl:if test="@NMORDDOM">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            NMORDDOM
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@NMORDDOM"/>
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
                <xsl:if test="@DIGITA">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            DIGITA
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@DIGITA"/>
                        </value>
                    </values>
                </xsl:if>
                <xsl:if test="@IDENCLIENT">
                    <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                            xsi:type="java:org.exolab.castor.mapping.MapItem">
                        <key xsi:type="java:java.lang.String">
                            IDENCLIENT
                        </key>
                        <value xsi:type="java:java.lang.String">
                            <xsl:value-of select="@IDENCLIENT"/>
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
