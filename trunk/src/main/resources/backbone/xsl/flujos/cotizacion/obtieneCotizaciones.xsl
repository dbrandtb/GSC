<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions">

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
            <item-list xsi:type="java:mx.com.aon.flujos.cotizacion.model.ConsultaCotizacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMSOLICI">
                    <xsl:element name="nmsolici">
                        <xsl:value-of select="@NMSOLICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDCIA">
                    <xsl:element name="cdcia">
                        <xsl:value-of select="@CDCIA" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@DSUNIECO">
                    <xsl:element name="descripcion-aseguradora">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                 <xsl:if test="@CDRAMO">
                    <xsl:element name="codigo-producto">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                 <xsl:if test="@DSRAMO">
                    <xsl:element name="descripcion-producto">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEINIVAL">
                    <xsl:element name="fecha">
                        <xsl:value-of select="@FEINIVAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fevencim">
                        <xsl:value-of select="@FEVENCIM" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@CDUNIECO">
                    <xsl:element name="codigo-aseguradora">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>         
                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nmpoliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSITUAC">
                    <xsl:element name="nmsituac">
                        <xsl:value-of select="@NMSITUAC" />
                    </xsl:element>
                </xsl:if>
				<xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
