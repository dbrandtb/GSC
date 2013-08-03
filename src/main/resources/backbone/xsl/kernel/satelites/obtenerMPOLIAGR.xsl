<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.kernel.model.MpoliagrVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDRAMO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nm-poliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDAGRUPA">
                    <xsl:element name="cd-agrupa">
                        <xsl:value-of select="@CDAGRUPA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nm-suplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMORDDOM">
                    <xsl:element name="nmorddom">
                        <xsl:value-of select="@NMORDDOM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDFORPAG">
                    <xsl:element name="cd-forpag">
                        <xsl:value-of select="@CDFORPAG" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSFORPAG">
                    <xsl:element name="ds-forpag">
                        <xsl:value-of select="@DSFORPAG" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDBANCO">
                    <xsl:element name="cd-banco">
                        <xsl:value-of select="@CDBANCO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDSUCURS">
                    <xsl:element name="cd-sucursal">
                        <xsl:value-of select="@CDSUCURS" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSBANCO">
                    <xsl:element name="ds-banco">
                        <xsl:value-of select="@DSBANCO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSSUCURS">
                    <xsl:element name="ds-sucursal">
                        <xsl:value-of select="@DSSUCURS" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDCUENTA">
                    <xsl:element name="cd-cuenta">
                        <xsl:value-of select="@CDCUENTA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDRAZON">
                    <xsl:element name="cd-razon">
                        <xsl:value-of select="@CDRAZON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@SWREGULA">
                    <xsl:element name="sw-regula">
                        <xsl:value-of select="@SWREGULA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDGESTOR">
                    <xsl:element name="cd-gestor">
                        <xsl:value-of select="@CDGESTOR" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDROL">
                    <xsl:element name="cd-rol">
                        <xsl:value-of select="@CDROL" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDTIPCTA">
                    <xsl:element name="cd-tipocuenta">
                        <xsl:value-of select="@CDTIPCTA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMCUENTA">
                    <xsl:element name="nmcuenta">
                        <xsl:value-of select="@NMCUENTA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDPERREG">
                    <xsl:element name="cd-perreg">
                        <xsl:value-of select="@CDPERREG" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDPAGCOM">
                    <xsl:element name="cd-pagcom">
                        <xsl:value-of select="@CDPAGCOM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="ds-nombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSDOMICIL">
                    <xsl:element name="ds-domicilio">
                        <xsl:value-of select="@DSDOMICIL" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSTITARJ">
                    <xsl:element name="ds-tipotarj">
                        <xsl:value-of select="@DSTITARJ" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FEULTREG">
                    <xsl:element name="fecha-ultreg">
                        <xsl:value-of select="@FEULTREG" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMDIGVER">
                    <xsl:element name="nm-digver">
                        <xsl:value-of select="@NMDIGVER" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@MUESTRA_CAMPO">
                    <xsl:element name="muestra-campos">
                        <xsl:value-of select="@MUESTRA_CAMPO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDTITARJ">
                    <xsl:element name="cd-tipo-tarjeta">
                        <xsl:value-of select="@CDTITARJ" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSTITARJ">
                    <xsl:element name="ds-tipo-tarjeta">
                        <xsl:value-of select="@DSTITARJ" />
                    </xsl:element>
                </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
