<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" 
                        xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    
    <!-- 
    <xsl:template match="/">
        <array-list xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
        </array-list>
    </xsl:template>
    <xsl:template match="rows">
        <xsl:for-each select="row">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>
    -->
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
    
    <!--
    <xsl:template match="row">
        <opcion-vO xsi:type="java:mx.com.aon.portal.model.configmenu.OpcionVO">
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
		<xsl:if test="@DSURL">
			<xsl:element name="ds-url">
				<xsl:value-of select="@DSURL"/>
			</xsl:element>
		</xsl:if>
        </opcion-vO>
    </xsl:template>
    -->

    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.configmenu.OpcionVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
				<xsl:if test="@CDTITULO">
	            	<xsl:element name="cd-titulo">
	                	<xsl:value-of select="@CDTITULO"/>
	            	</xsl:element>
	        	</xsl:if>
				<xsl:if test="@DSTITULO">
					<xsl:element name="ds-titulo">
						<xsl:value-of select="@DSTITULO"/>
					</xsl:element>
                    <xsl:element name="ds-titulo-des">
                        <xsl:value-of select="@DSTITULO"/>
                    </xsl:element>
				</xsl:if>
				<xsl:if test="@DSURL">
					<xsl:element name="ds-url">
						<xsl:value-of select="@DSURL"/>
					</xsl:element>
				</xsl:if>
				<xsl:if test="@SWTIPDES">
					<xsl:element name="ds-tip-des">
						<xsl:value-of select="@SWTIPDES"/>
					</xsl:element>
				</xsl:if>
          </item-list>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>        