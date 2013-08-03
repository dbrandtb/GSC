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
            <item-list xsi:type="java:mx.com.aon.flujos.endoso.model.AgrupadoresVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@DSBANCO">
        			<xsl:element name="banco">
        				<xsl:value-of select="@DSBANCO"/>
        			</xsl:element>
        		</xsl:if>
                <xsl:if test="@DSNOMBRE">
        			<xsl:element name="nombre">
        				<xsl:value-of select="@DSNOMBRE"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSDOMICIL">
        			<xsl:element name="domicilio">
        				<xsl:value-of select="@DSDOMICIL"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSFORPAG">
        			<xsl:element name="instrumento">
        				<xsl:value-of select="@DSFORPAG"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@FEVENCE">
        			<xsl:element name="fecha-vencimiento">
        				<xsl:value-of select="@FEVENCE"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSTITARJ">
        			<xsl:element name="tipo-tarjeta">
        				<xsl:value-of select="@DSTITARJ"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@NMCUENTA">
        			<xsl:element name="num-cuenta">
        				<xsl:value-of select="@NMCUENTA"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@DSSUCURS">
        			<xsl:element name="sucursal">
        				<xsl:value-of select="@DSSUCURS"/>
        			</xsl:element>
        		</xsl:if>
        		
        		<xsl:if test="@CDPERSON">
        			<xsl:element name="cd-person">
        				<xsl:value-of select="@CDPERSON"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@NMORDDOM">
        			<xsl:element name="cd-domicilio">
        				<xsl:value-of select="@NMORDDOM"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@CDFORPAG">
        			<xsl:element name="cd-instrumento-pago">
        				<xsl:value-of select="@CDFORPAG"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@CDBANCO">
        			<xsl:element name="cd-banco">
        				<xsl:value-of select="@CDBANCO"/>
        			</xsl:element>
        		</xsl:if>
        		<xsl:if test="@CDSUCURS">
        			<xsl:element name="cd-sucursal">
        				<xsl:value-of select="@CDSUCURS"/>
        			</xsl:element>
        		</xsl:if>
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet> 