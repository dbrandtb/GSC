<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title_o']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.AsociarOrdenTrabajoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        <xsl:if test="@CDASOCIA">
		            <xsl:element name="cdasocia">
		                <xsl:value-of select="@CDASOCIA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFORORD">
		            <xsl:element name="cdforord">
		                <xsl:value-of select="@CDFORORD" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSFORMATOORDEN">
		            <xsl:element name="dsformatoorden">
		                <xsl:value-of select="@DSFORMATOORDEN" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDPROCES">
		            <xsl:element name="cdproces">
		                <xsl:value-of select="@CDPROCES" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSPROCESO">
		            <xsl:element name="dsproces">
		                <xsl:value-of select="@DSPROCESO" />
		            </xsl:element>
		        </xsl:if>
		         <xsl:if test="@CDELEMENTO">
		            <xsl:element name="cdelemento">
		                <xsl:value-of select="@CDELEMENTO" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSELEMEN">
		            <xsl:element name="dselemen">
		                <xsl:value-of select="@DSELEMEN" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDPERSON">
		            <xsl:element name="cdperson">
		                <xsl:value-of select="@CDPERSON" />
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
		        <xsl:if test="@CDFOLIOAON">
		            <xsl:element name="cdfolioaon">
		                <xsl:value-of select="@CDFOLIOAON" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSFOLIOAON">
		            <xsl:element name="dsfolioaon">
		                <xsl:value-of select="@DSFOLIOAON" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFOLIOCIA">
		            <xsl:element name="cdfoliocia">
		                <xsl:value-of select="@CDFOLIOCIA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSFOLIOCIA">
		            <xsl:element name="dsfoliocia">
		                <xsl:value-of select="@DSFOLIOCIA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSFORMAON">
		            <xsl:element name="dsformaon">
		                <xsl:value-of select="@DSFORMAON" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@DSFORMCIA">
		            <xsl:element name="dsformcia">
		                <xsl:value-of select="@DSFORMCIA" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFOLAONINI">
		            <xsl:element name="cdfolaonini">
		                <xsl:value-of select="@CDFOLAONINI" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFOLAONFIN">
		            <xsl:element name="cdfolaonfin">
		                <xsl:value-of select="@CDFOLAONFIN" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFOLCIAINI">
		            <xsl:element name="cdfolciaini">
		                <xsl:value-of select="@CDFOLCIAINI" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDFOLCIAFIN">
		            <xsl:element name="cdfolciafin">
		                <xsl:value-of select="@CDFOLCIAFIN" />
		            </xsl:element>
		        </xsl:if>
		        
		        
		        
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>