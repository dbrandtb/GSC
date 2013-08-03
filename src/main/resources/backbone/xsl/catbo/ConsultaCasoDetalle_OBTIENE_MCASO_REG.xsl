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
            <item-list xsi:type="java:mx.com.aon.catbo.model.CasoDetalleVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

		        <xsl:if test="@CDPROCESO">
		            <xsl:element name="cdproceso">
		                <xsl:value-of select="@CDPROCESO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSPROCESO">
		            <xsl:element name="dsproceso">
		                <xsl:value-of select="@DSPROCESO" />
		            </xsl:element>
		        </xsl:if>
                <xsl:if test="@CDPRIORD">
                    <xsl:element name="cdpriord">
                        <xsl:value-of select="@CDPRIORD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPRIORIDAD">
                    <xsl:element name="dspriord">
                        <xsl:value-of select="@DSPRIORIDAD" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDNIVATN">
                    <xsl:element name="cdnivatn">
                        <xsl:value-of select="@CDNIVATN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSNIVATN">
                    <xsl:element name="dsnivatn">
                        <xsl:value-of select="@DSNIVATN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMCASO">
                    <xsl:element name="nmcaso">
                        <xsl:value-of select="@NMCASO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUSUARIO">
                    <xsl:element name="cdusuario">
                        <xsl:value-of select="@CDUSUARIO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUSUARI">
                    <xsl:element name="cdusuari">
                        <xsl:value-of select="@CDUSUARI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDUNIECO">
                    <xsl:element name="cdunieco">
                        <xsl:value-of select="@CDUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSUNIECO">
                    <xsl:element name="dsunieco">
                        <xsl:value-of select="@DSUNIECO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PORCENTAJE">
                    <xsl:element name="porcentaje">
                        <xsl:value-of select="@PORCENTAJE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@COLOR">
                    <xsl:element name="color">
                        <xsl:value-of select="@COLOR" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDORDENTRABAJO">
                    <xsl:element name="cdordentrabajo">
                        <xsl:value-of select="@CDORDENTRABAJO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDNUMERORDENCIA">
                    <xsl:element name="cdnumerordencia">
                        <xsl:value-of select="@CDNUMERORDENCIA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMODULO">
                    <xsl:element name="cdmodulo">
                        <xsl:value-of select="@CDMODULO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDSTATUS">
                    <xsl:element name="cdstatus">
                        <xsl:value-of select="@CDSTATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TIEMPORESTANTEPARAATENDER">
                    <xsl:element name="tiemporestanteparaatender">
                        <xsl:value-of select="@TIEMPORESTANTEPARAATENDER" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSMODULO">
                    <xsl:element name="dsmodulo">
                        <xsl:value-of select="@DSMODULO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TIEMPORESTANTEPARAESCALAR">
                    <xsl:element name="tiemporestanteparaescalar">
                        <xsl:value-of select="@TIEMPORESTANTEPARAESCALAR" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMVECESCOMPRA">
                    <xsl:element name="nmvecescompra">
                        <xsl:value-of select="@NMVECESCOMPRA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFORMATOORDEN">
                    <xsl:element name="cdformatoorden">
                        <xsl:value-of select="@CDFORMATOORDEN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMOVIMIENTO">
                    <xsl:element name="nmovimiento">
                        <xsl:value-of select="@NMOVIMIENTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSSTATUS">
                    <xsl:element name="dsstatus">
                        <xsl:value-of select="@DSSTATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMCOMPRA">
                    <xsl:element name="nm-compra">
                        <xsl:value-of select="@NMCOMPRA" />
                    </xsl:element>
                </xsl:if>
                            
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>