<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:template match="/">
        <resultado-dao xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO">
            <xsl:choose>
                <xsl:when test="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value='S' ">
                    <xsl:apply-templates select="result/storedProcedure/outparam/rows"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="error"/>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:element name="estado">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_TIPOERROR']/@value"/>
            </xsl:element>
        </resultado-dao>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <cursor xsi:type="java:mx.com.ice.kernel.to.ResultadoDAO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        
                <xsl:if test="@CDPANTAL">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdpantal
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDPANTAL"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDBLOQUE">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdbloque
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDBLOQUE"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDRAMO">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdramo
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDRAMO"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDPROCES">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdproces
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDPROCES"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDCAMPO">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdcampo
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDCAMPO"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDTIPSIT">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdtipsit
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDTIPSIT"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDGARANT">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdgarant
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDGARANT"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDTIPOBJ">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdtipobj
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDTIPOBJ"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@CDATRIBU">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                cdatribu
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@CDATRIBU"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWVISIBL">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swvisibl
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWVISIBL"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWEDITAB">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                sweditab
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWEDITAB"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@SWOBLIGA">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                swobliga
                            </key>
                            <value xsi:type="java:java.lang.String">
                                <xsl:value-of select="@SWOBLIGA"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@NMORDEN">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                nmorden
                            </key>
                            <value xsi:type="java:java.lang.Integer">
                                <xsl:value-of select="@NMORDEN"/>
                            </value>
                        </values>
                    </xsl:if>
                    <xsl:if test="@NMSECCIO">
                        <values xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="java:org.exolab.castor.mapping.MapItem">
                            <key xsi:type="java:java.lang.String">
                                nmseccio
                            </key>
                            <value xsi:type="java:java.lang.Integer">
                                <xsl:value-of select="@NMSECCIO"/>
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