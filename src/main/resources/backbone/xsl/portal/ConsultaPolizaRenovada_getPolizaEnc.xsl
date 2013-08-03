<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<wrapper-resultados
			xsi:type="java:mx.com.aon.portal.util.WrapperResultados"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
			<xsl:element name="msg-id">
				<xsl:value-of
					select="result/storedProcedure/outparam[@id='pv_msg_id_o']/@value" />
			</xsl:element>
			<xsl:element name="msg">
				<xsl:value-of
					select="result/storedProcedure/outparam[@id='pv_title_o']/@value" />
			</xsl:element>

			<xsl:apply-templates
				select="result/storedProcedure/outparam/rows" />
		</wrapper-resultados>
	</xsl:template>


	<xsl:template match="rows">
		<xsl:for-each select="row">
			<item-list
				xsi:type="java:mx.com.aon.portal.model.EncabezadoPolizaVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

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
                <xsl:if test="@CDRAMO">
                    <xsl:element name="cdramo">
                        <xsl:value-of select="@CDRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSRAMO">
                    <xsl:element name="dsramo">
                        <xsl:value-of select="@DSRAMO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLANT">
                    <xsl:element name="nmpolant">
                        <xsl:value-of select="@NMPOLANT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIZA">
                    <xsl:element name="nmpoliza">
                        <xsl:value-of select="@NMPOLIZA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSUPLEM">
                    <xsl:element name="nmsuplem">
                        <xsl:value-of select="@NMSUPLEM" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@ESTADO">
                    <xsl:element name="estado">
                        <xsl:value-of select="@ESTADO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEEFECTO">
                    <xsl:element name="feefecto">
                        <xsl:value-of select="@FEEFECTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fevencim">
                        <xsl:value-of select="@FEVENCIM" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cdperson">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@ASEGURADO">
                    <xsl:element name="asegurado">
                        <xsl:value-of select="@ASEGURADO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPERPAG">
                    <xsl:element name="cdperpag">
                        <xsl:value-of select="@CDPERPAG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSPERPAG">
                    <xsl:element name="dsperpag">
                        <xsl:value-of select="@DSPERPAG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSFORPAG">
                    <xsl:element name="dsforpag">
                        <xsl:value-of select="@DSFORPAG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDFORPAG">
                    <xsl:element name="cdforpag">
                        <xsl:value-of select="@CDFORPAG" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWESTADO">
                    <xsl:element name="swestado">
                        <xsl:value-of select="@SWESTADO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSOLICI">
                    <xsl:element name="nmsolici">
                        <xsl:value-of select="@NMSOLICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEAUTORI">
                    <xsl:element name="feautori">
                        <xsl:value-of select="@FEAUTORI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMOTANU">
                    <xsl:element name="cdmotanu">
                        <xsl:value-of select="@CDMOTANU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEANULAC">
                    <xsl:element name="feanulac">
                        <xsl:value-of select="@FEANULAC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWAUTORI">
                    <xsl:element name="swautori">
                        <xsl:value-of select="@SWAUTORI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMONEDA">
                    <xsl:element name="cdmoneda">
                        <xsl:value-of select="@CDMONEDA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEINISUS">
                    <xsl:element name="feinisus">
                        <xsl:value-of select="@FEINISUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEFINSUS">
                    <xsl:element name="fefinsus">
                        <xsl:value-of select="@FEFINSUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@OTTEMPOT">
                    <xsl:element name="ottempot">
                        <xsl:value-of select="@OTTEMPOT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@HHEFECTO">
                    <xsl:element name="hhefecto">
                        <xsl:value-of select="@HHEFECTO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEPROREN">
                    <xsl:element name="feproren">
                        <xsl:value-of select="@FEPROREN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMRENOVA">
                    <xsl:element name="nmrenova">
                        <xsl:value-of select="@NMRENOVA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FERECIBO">
                    <xsl:element name="ferecibo">
                        <xsl:value-of select="@FERECIBO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEULTSIN">
                    <xsl:element name="feultsin">
                        <xsl:value-of select="@FEULTSIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMNUMSIN">
                    <xsl:element name="nmnumsin">
                        <xsl:value-of select="@NMNUMSIN" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPCOA">
                    <xsl:element name="cdtipcoa">
                        <xsl:value-of select="@CDTIPCOA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWTARIFI">
                    <xsl:element name="swtarifi">
                        <xsl:value-of select="@SWTARIFI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWABRIDO">
                    <xsl:element name="swabrido">
                        <xsl:value-of select="@SWABRIDO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FEEMISIO">
                    <xsl:element name="feemisio">
                        <xsl:value-of select="@FEEMISIO" />
                    </xsl:element>
                </xsl:if> 
                <xsl:if test="@NMPOLIEX">
                    <xsl:element name="nmpoliex">
                        <xsl:value-of select="@NMPOLIEX" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMCUADRO">
                    <xsl:element name="nmcuadro">
                        <xsl:value-of select="@NMCUADRO" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PORREDAU">
                    <xsl:element name="porredau">
                        <xsl:value-of select="@PORREDAU" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWCONSOL">
                    <xsl:element name="swconsol">
                        <xsl:value-of select="@SWCONSOL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLNVA">
                    <xsl:element name="nmpolnva">
                        <xsl:value-of select="@NMPOLNVA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@FESOLICI">
                    <xsl:element name="fesolici">
                        <xsl:value-of select="@FESOLICI" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDRAMANT">
                    <xsl:element name="cdramant">
                        <xsl:value-of select="@CDRAMANT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDMEJRED">
                    <xsl:element name="cdmejred">
                        <xsl:value-of select="@CDMEJRED" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLDOC">
                    <xsl:element name="nmpoldoc">
                        <xsl:value-of select="@NMPOLDOC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMPOLIZA2">
                    <xsl:element name="nmpoliza2">
                        <xsl:value-of select="@NMPOLIZA2" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMRENOVE">
                    <xsl:element name="nmrenove">
                        <xsl:value-of select="@NMRENOVE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@NMSUPLEE">
                    <xsl:element name="nmsuplee">
                        <xsl:value-of select="@NMSUPLEE" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TTIPCAMC">
                    <xsl:element name="ttipcamc">
                        <xsl:value-of select="@TTIPCAMC" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@TTIPCAMV">
                    <xsl:element name="ttipcamv">
                        <xsl:value-of select="@TTIPCAMV" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@SWPATENT">
                    <xsl:element name="swpatent">
                        <xsl:value-of select="@SWPATENT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PRIMA_TOTAL">
                    <xsl:element name="prima_total">
                        <xsl:value-of select="@PRIMA_TOTAL" />
                    </xsl:element>
                </xsl:if>
                             
			</item-list>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>