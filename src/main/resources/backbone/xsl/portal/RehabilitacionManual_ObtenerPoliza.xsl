<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

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
            <item-list xsi:type="java:mx.com.aon.portal.model.RehabilitacionManual_PolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@ASEGURADO">
                    <xsl:element name="ds-asegurado">
                        <xsl:value-of select="@ASEGURADO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ASEG">
                    <xsl:element name="cd-aseguradora">
                        <xsl:value-of select="@ASEG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ASEGURADORA">
                    <xsl:element name="ds-aseguradora">
                        <xsl:value-of select="@ASEGURADORA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@PROD">
                    <xsl:element name="cd-producto">
                        <xsl:value-of select="@PROD" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@PRODUCTO">
                    <xsl:element name="ds-producto">
                        <xsl:value-of select="@PRODUCTO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@POLIZA">
                    <xsl:element name="nm-poliza">
                        <xsl:value-of select="@POLIZA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@INCISO">
                    <xsl:element name="nm-inciso">
                        <xsl:value-of select="@INCISO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FECHA_INICIO">
                    <xsl:element name="fecha-inciso">
                        <xsl:value-of select="@FECHA_INICIO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FECANCEL">
                    <xsl:element name="fecha-cancelacion">
                        <xsl:value-of select="@FECANCEL" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fecha-vencimiento">
                        <xsl:value-of select="@FEVENCIM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@FEEFECTO">
                    <xsl:element name="fecha-inciso">
                        <xsl:value-of select="@FEEFECTO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDMONEDA">
                    <xsl:element name="moneda">
                        <xsl:value-of select="@CDMONEDA" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nm-suplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@CDRAZON">
                    <xsl:element name="cd-razon-cancelacion">
                        <xsl:value-of select="@CDRAZON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@DSRAZON">
                    <xsl:element name="ds-razon-cancelacion">
                        <xsl:value-of select="@DSRAZON" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@NMCANCEL">
                    <xsl:element name="nm-cancel">
                        <xsl:value-of select="@NMCANCEL" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@INCISOEXT">
                    <xsl:element name="inciso-ext">
                        <xsl:value-of select="@INCISOEXT" />
                    </xsl:element>
                </xsl:if>

                <xsl:if test="@COMENTARIOS">
                    <xsl:element name="comentarios-cancelacion">
                        <xsl:value-of select="@COMENTARIOS" />
                    </xsl:element>
                </xsl:if>
                
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
