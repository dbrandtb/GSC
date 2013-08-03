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
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.RecibosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@NMRECIBO">
        			<xsl:element name="nmrecibo">
        				<xsl:value-of select="@NMRECIBO"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@FEINICIO">
        			<xsl:element name="feinicio">
        				<xsl:value-of select="@FEINICIO"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@FEFINAL">
                    <xsl:element name="fefinal">
                        <xsl:value-of select="@FEFINAL"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEEMISIO">
                    <xsl:element name="feemisio">
                        <xsl:value-of select="@FEEMISIO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDESTADO">
                    <xsl:element name="cdestado">
                        <xsl:value-of select="@CDESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEESTADO">
                    <xsl:element name="feestado">
                        <xsl:value-of select="@FEESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PTIMPORT">
                    <xsl:element name="ptimport">
                        <xsl:value-of select="@PTIMPORT"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEEXPEDI">
                    <xsl:element name="feexpedi">
                        <xsl:value-of select="@FEEXPEDI"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSESTADO">
                    <xsl:element name="dsestado">
                        <xsl:value-of select="@DSESTADO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TIPORECI">
                    <xsl:element name="cd-tipo-recibo">
                        <xsl:value-of select="@TIPORECI"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSTIPORE">
                    <xsl:element name="ds-tipo-recibo">
                        <xsl:value-of select="@DSTIPORE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMIMPRES">
                	<xsl:element name="nmreciboexterno">
                		<xsl:value-of select="@NMIMPRES"/>
                	</xsl:element>
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 