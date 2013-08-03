<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
            <item-list xsi:type="java:mx.com.aon.flujos.renovacion.model.RenovacionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@ASEGURADO">
        			<xsl:element name="asegurado">
        				<xsl:value-of select="@ASEGURADO"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@ASEGURADORA">
                    <xsl:element name="cd-cia">
                        <xsl:value-of select="@ASEGURADORA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSASEGURADORA">
        			<xsl:element name="aseguradora">
        				<xsl:value-of select="@DSASEGURADORA"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@PRODUCTO">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@PRODUCTO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="producto">
                        <xsl:value-of select="@DSRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CLI">
                    <xsl:element name="cd-cliente">
                        <xsl:value-of select="@CLI"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CLIENTE">
                    <xsl:element name="cliente">
                        <xsl:value-of select="@CLIENTE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIEX_EXTANT">
                    <xsl:element name="poliza-anterior">
                        <xsl:value-of select="@NMPOLIEX_EXTANT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLANT">
                    <xsl:element name="nm-poliza-anterior">
                        <xsl:value-of select="@NMPOLANT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@POLIZA_RENOVACION">
                    <xsl:element name="poliza-renovacion">
                        <xsl:value-of select="@POLIZA_RENOVACION"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@INCISO">
                    <xsl:element name="inciso">
                        <xsl:value-of select="@INCISO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PRIMA">
                    <xsl:element name="prima">
                        <xsl:value-of select="@PRIMA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FECHA_RENOVACION">
                    <xsl:element name="fecha-renova">
                        <xsl:value-of select="@FECHA_RENOVACION"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nm-poliza">
                        <xsl:value-of select="@NMPOLIZA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSITUAC">
                    <xsl:element name="nm-situac">
                        <xsl:value-of select="@NMSITUAC"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cd-unieco">
                        <xsl:value-of select="@CDUNIECO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMANNO">
                    <xsl:element name="nmanno">
                        <xsl:value-of select="@NMANNO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMMES">
                    <xsl:element name="nmmes">
                        <xsl:value-of select="@NMMES"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWAPROBADA">
                    <xsl:element name="sw-aprobada">
                        <xsl:value-of select="@SWAPROBADA"/>
                    </xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 