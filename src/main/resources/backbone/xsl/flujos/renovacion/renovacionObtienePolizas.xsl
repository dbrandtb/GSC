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
                <xsl:if test="@ASEG">
                    <xsl:element name="cd-cia">
                        <xsl:value-of select="@ASEG"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ASEGURADORA">
        			<xsl:element name="aseguradora">
        				<xsl:value-of select="@ASEGURADORA"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@PROD">
                    <xsl:element name="cd-ramo">
                        <xsl:value-of select="@PROD"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PRODUCTO">
                    <xsl:element name="producto">
                        <xsl:value-of select="@PRODUCTO"/>
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
                <xsl:if test="@POLIZA">
                    <xsl:element name="poliza">
                        <xsl:value-of select="@POLIZA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@INCISO">
                    <xsl:element name="inciso">
                        <xsl:value-of select="@INCISO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FECHA_RENOVA">
                    <xsl:element name="fecha-renova">
                        <xsl:value-of select="@FECHA_RENOVA"/>
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
                <xsl:if test="@SWRENOVA">
                    <xsl:element name="renovar">
                        <xsl:value-of select="@SWRENOVA"/>
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
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 