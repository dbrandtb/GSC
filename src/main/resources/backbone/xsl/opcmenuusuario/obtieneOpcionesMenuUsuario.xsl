<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
				xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
                
	<xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='PV_MSG_ID_O']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='PV_TITLE_O']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>
    
    <xsl:template match="rows">
        <xsl:for-each select="row">
			<item-list xsi:type="java:mx.com.aon.portal.model.opcmenuusuario.OpcionMenuVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
        	<xsl:if test="@CDMENU">
            	<xsl:element name="cd-menu">
	                <xsl:value-of select="@CDMENU"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@DSMENU">
	            <xsl:element name="ds-menu">
                	<xsl:value-of select="@DSMENU"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDNIVEL">
	            <xsl:element name="cd-nivel">
                	<xsl:value-of select="@CDNIVEL"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDNIVEL_PADRE">
	            <xsl:element name="cd-nivel-padre">
                	<xsl:value-of select="@CDNIVEL_PADRE"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@DSMENU_EST">
	            <xsl:element name="ds-menu-est">
                	<xsl:value-of select="@DSMENU_EST"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDRAMO">
	            <xsl:element name="cd-ramo">
                	<xsl:value-of select="@CDRAMO"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@DSRAMO">
	            <xsl:element name="ds-ramo">
                	<xsl:value-of select="@DSRAMO"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDTIPSIT">
	            <xsl:element name="cd-tipsit">
                	<xsl:value-of select="@CDTIPSIT"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@DSTIPSIT">
	            <xsl:element name="ds-tipsit">
                	<xsl:value-of select="@DSTIPSIT"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDTITULO">
	            <xsl:element name="cd-titulo">
                	<xsl:value-of select="@CDTITULO"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@DSTITULO">
	            <xsl:element name="ds-titulo">
                	<xsl:value-of select="@DSTITULO"/>
            	</xsl:element>
        	</xsl:if>
        	<xsl:if test="@CDESTADO">
	            <xsl:element name="cd-estado">
                	<xsl:value-of select="@CDESTADO"/>
            	</xsl:element>
        	</xsl:if>
                   
		</item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>