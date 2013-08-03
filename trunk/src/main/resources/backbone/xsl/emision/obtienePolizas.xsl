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
            <item-list xsi:type="java:mx.com.aon.procesos.emision.model.EmisionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDUNIECO">
        			<xsl:element name="cd-unieco">
        				<xsl:value-of select="@CDUNIECO"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@CDRAMO">
        			<xsl:element name="cd-ramo">
        				<xsl:value-of select="@CDRAMO"/>
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
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="aseguradora">
                        <xsl:value-of select="@DSUNIECO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="producto">
                        <xsl:value-of select="@DSRAMO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIEX">
                    <xsl:element name="poliza">
                        <xsl:value-of select="@NMPOLIEX"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPERPAG">
                    <xsl:element name="periocidad">
                        <xsl:value-of select="@DSPERPAG"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="nombre-persona">
                        <xsl:value-of select="@DSNOMBRE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@RFC">
                    <xsl:element name="rfc">
                        <xsl:value-of select="@RFC"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@VIGENCIA">
                    <xsl:element name="vigencia">
                        <xsl:value-of select="@VIGENCIA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFORPAG">
                    <xsl:element name="inst-pago">
                        <xsl:value-of select="@DSFORPAG"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PRIMA">
                    <xsl:element name="prima-total">
                        <xsl:value-of select="@PRIMA"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@INCISO">
                    <xsl:element name="inciso">
                        <xsl:value-of select="@INCISO"/>
                    </xsl:element>
                </xsl:if>
                

                <xsl:if test="@FEEFECTO">
                    <xsl:element name="fe-efecto">
                        <xsl:value-of select="@FEEFECTO"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fe-vencim">
                        <xsl:value-of select="@FEVENCIM"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIAGE">
                    <xsl:element name="cd-uni-age">
                        <xsl:value-of select="@CDUNIAGE"/>
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWESTADO">
                	<xsl:element name="sw-estado">
                		<xsl:value-of select="@SWESTADO"/>
                	</xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                	<xsl:element name="status">
                		<xsl:value-of select="@STATUS"/>
                	</xsl:element>                	
                </xsl:if>
                <xsl:if test="@SWCANCEL">
                	<xsl:element name="sw-cancel">
                		<xsl:value-of select="@SWCANCEL"/>
                	</xsl:element>                	
                </xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 