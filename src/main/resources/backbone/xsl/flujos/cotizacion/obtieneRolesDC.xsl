<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.RolVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

                <xsl:if test="@CDROL">
                    <xsl:element name="codigo-rol">
                        <xsl:value-of select="@CDROL" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSROL">
                    <xsl:element name="descripcion-rol">
                        <xsl:value-of select="@DSROL" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDNIVEL ">
                    <xsl:element name="codigo-nivel">
                        <xsl:value-of select="@CDNIVEL " />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDCOMPO">
                    <xsl:element name="codigo-compo">
                        <xsl:value-of select="@CDCOMPO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMAXIMO">
                    <xsl:element name="numero-maximo">
                        <xsl:value-of select="@NMAXIMO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWDOMICI">
                    <xsl:element name="switch-domicilio">
                        <xsl:value-of select="@SWDOMICI" />
                    </xsl:element>
                </xsl:if>
                
              	<xsl:if test="@CDPERSON">
                    <xsl:element name="codigo-persona">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="descripcion-nombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDRFC">
                    <xsl:element name="codigo-r-f-c">
                        <xsl:value-of select="@CDRFC" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@FENACIMI">
                    <xsl:element name="fecha-nacimiento">
                        <xsl:value-of select="@FENACIMI" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@OTFISJUR">
                    <xsl:element name="ot-fis-jur">
                        <xsl:value-of select="@OTFISJUR" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSFISJUR">
                    <xsl:element name="ds-fis-jur">
                        <xsl:value-of select="@DSFISJUR" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@SWOBLIGA">
                    <xsl:element name="requerido">
                        <xsl:value-of select="@SWOBLIGA" />
                    </xsl:element>
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
