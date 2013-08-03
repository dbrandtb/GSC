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
				xsi:type="java:mx.com.aon.portal.model.CoberturasProductoVO"
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">               
          		                
                <xsl:if test="@CDGARANT">
                    <xsl:element name="cdgarant">
                        <xsl:value-of select="@CDGARANT" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@DSGARANT">
                    <xsl:element name="dsgarant">
                        <xsl:value-of select="@DSGARANT" />
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
                <xsl:if test="@CDCAPITA">
                    <xsl:element name="cdcapita">
                        <xsl:value-of select="@CDCAPITA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@STATUS">
                    <xsl:element name="status">
                        <xsl:value-of select="@STATUS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDTIPBCA">
                    <xsl:element name="cdtipbca">
                        <xsl:value-of select="@CDTIPBCA" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PTVALBAS">
                    <xsl:element name="ptvalbas">
                        <xsl:value-of select="@PTVALBAS" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@PTTASA">
                    <xsl:element name="pttasa">
                        <xsl:value-of select="@PTTASA" />
                    </xsl:element>
                </xsl:if>
                 <xsl:if test="@CDTIPFRA">
                    <xsl:element name="cdtipfra">
                        <xsl:value-of select="@CDTIPFRA" />
                    </xsl:element>
                </xsl:if>
                 <xsl:if test="@SWMANUAL">
                    <xsl:element name="swmanual">
                        <xsl:value-of select="@SWMANUAL" />
                    </xsl:element>
                </xsl:if>
                <xsl:if test="@CDPLAN">
                    <xsl:element name="cdplan">
                        <xsl:value-of select="@CDPLAN" />
                	</xsl:element>
                </xsl:if>	                
				<xsl:if test="@NMCUMULO">
                    <xsl:element name="nmcumulo">
                        <xsl:value-of select="@NMCUMULO" />
                	</xsl:element>                
                </xsl:if>
				<xsl:if test="@CDTIPCUM">
                    <xsl:element name="cdtipcum">
                        <xsl:value-of select="@CDTIPCUM" />
                	</xsl:element>                
                </xsl:if>
				<xsl:if test="@NMCESION">
                    <xsl:element name="nmcesion">
                        <xsl:value-of select="@NMCESION" />
                	</xsl:element>                
                </xsl:if>
				<xsl:if test="@CDMONEDA">
                    <xsl:element name="cdmoneda">
                        <xsl:value-of select="@CDMONEDA" />
                	</xsl:element>                
                </xsl:if>                
				<xsl:if test="@SWEXCUM">
                    <xsl:element name="swexcum">
                        <xsl:value-of select="@SWEXCUM" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@PTIMPEXC">
                    <xsl:element name="ptimpexc">
                        <xsl:value-of select="@PTIMPEXC" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@PTIMPFAC">
                    <xsl:element name="ptimpfac">
                        <xsl:value-of select="@PTIMPFAC" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@PTFACEFE">
                    <xsl:element name="ptfacefe">
                        <xsl:value-of select="@PTFACEFE" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@IMPORREA">
                    <xsl:element name="imporrea">
                        <xsl:value-of select="@IMPORREA" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@SWINCAPA">
                    <xsl:element name="swincapa">
                        <xsl:value-of select="@SWINCAPA" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@CDGRUPO">
                    <xsl:element name="cdgrupo">
                        <xsl:value-of select="@CDGRUPO" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@SWREAS">
                    <xsl:element name="swreas">
                        <xsl:value-of select="@SWREAS" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@CDAGRUPA">
                    <xsl:element name="cdagrupa">
                        <xsl:value-of select="@CDAGRUPA" />
                	</xsl:element>                
                </xsl:if>
                <xsl:if test="@FEVENCIM">
                    <xsl:element name="fevencim">
                        <xsl:value-of select="@FEVENCIM" />
                	</xsl:element>                
                </xsl:if>                                               				
			</item-list>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>