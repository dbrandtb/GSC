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
            <item-list xsi:type="java:mx.com.aon.portal.model.IncisosPolizaRenovarVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cdunieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                 <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
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
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPSIT">
                    <xsl:element name="cdtipsit">
                        <xsl:value-of select="@CDTIPSIT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWREDUCI">
                    <xsl:element name="swreduci">
                        <xsl:value-of select="@SWREDUCI" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@CDAGRUPA">
                    <xsl:element name="cdagrupa">
                        <xsl:value-of select="@CDAGRUPA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cdestado">
                        <xsl:value-of select="@CDESTADO" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@FEFECSIT">
                    <xsl:element name="fefecsit">
                        <xsl:value-of select="@FEFECSIT" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@FECHAREF">
                    <xsl:element name="fecharef">
                        <xsl:value-of select="@FECHAREF" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@CDGRUPO">
                    <xsl:element name="cdgrupo">
                        <xsl:value-of select="@CDGRUPO" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@NMSITUAEXT">
                    <xsl:element name="nmsituaext">
                        <xsl:value-of select="@NMSITUAEXT" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@NMSITAUX">
                    <xsl:element name="nmsitaux">
                        <xsl:value-of select="@NMSITAUX" />
                    </xsl:element>
                </xsl:if>
                    <xsl:if test="@NMSBSITEXT">
                    <xsl:element name="nmsbsitext">
                        <xsl:value-of select="@NMSBSITEXT" />
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>