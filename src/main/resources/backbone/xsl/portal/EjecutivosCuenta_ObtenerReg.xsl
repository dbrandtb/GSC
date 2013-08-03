<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format"
                xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

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
            <item-list xsi:type="java:mx.com.aon.portal.model.EjecutivoCuentaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

				<xsl:if test="@CDELEMENTO">
					<xsl:element name="cd-elemento">
						<xsl:value-of select="@CDELEMENTO"/>
					</xsl:element>
				</xsl:if>

            <xsl:if test="@CDPERSON">
                <xsl:element name="cd-person">
                    <xsl:value-of select="@CDPERSON"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSNOMBRE">
                <xsl:element name="ds-nombre">
                    <xsl:value-of select="@DSNOMBRE"/>
                </xsl:element>
            </xsl:if>

           <xsl:if test="@CDGRUPO">
                <xsl:element name="cd-grupo">
                    <xsl:value-of select="@CDGRUPO"/>
                </xsl:element>
            </xsl:if> 
            
            <xsl:if test="@DSGRUPO">
                <xsl:element name="des-grupo">
                    <xsl:value-of select="@DSGRUPO"/>
                </xsl:element>
            </xsl:if> 
            
            <xsl:if test="@CDLINCTA">
                <xsl:element name="cd-lin-cta">
                    <xsl:value-of select="@CDLINCTA"/>
                </xsl:element>
            </xsl:if> 
            
            <xsl:if test="@DSLINCTA">
                <xsl:element name="ds-lin-cta">
                    <xsl:value-of select="@DSLINCTA"/>
                </xsl:element>
            </xsl:if> 
            
            <xsl:if test="@CDTIPRAM">
                <xsl:element name="cd-tip-ram">
                    <xsl:value-of select="@CDTIPRAM"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSTIPRAM">
                <xsl:element name="ds-tip-ram">
                    <xsl:value-of select="@DSTIPRAM"/>
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

            <xsl:if test="@CDAGENTE">
                <xsl:element name="cd-agente">
                    <xsl:value-of select="@CDAGENTE"/>
                </xsl:element>
            </xsl:if> 
            
             <xsl:if test="@NOM_AGENTE">
                <xsl:element name="nom-agente">
                    <xsl:value-of select="@NOM_AGENTE"/>
                </xsl:element>
            </xsl:if> 
            
             <xsl:if test="@CDESTADO">
                <xsl:element name="cd-estado">
                    <xsl:value-of select="@CDESTADO"/>
                </xsl:element>
            </xsl:if> 
            
             <xsl:if test="@DSESTADO">
                <xsl:element name="ds-estado">
                    <xsl:value-of select="@DSESTADO"/>
                </xsl:element>
            </xsl:if> 
            
            
            <xsl:if test="@FEINICIO">
                <xsl:element name="fe-inicio">
                    <xsl:value-of select="@FEINICIO"/>
                </xsl:element>
            </xsl:if> 
            
             <xsl:if test="@FEFIN">
                <xsl:element name="fe-fin">
                    <xsl:value-of select="@FEFIN"/>
                </xsl:element>
            </xsl:if> 
            
             <xsl:if test="@SWNIVELPOSTERIOR">
                <xsl:element name="sw-nivel-posterior">
                    <xsl:value-of select="@SWNIVELPOSTERIOR"/>
                </xsl:element>
            </xsl:if> 

             <xsl:if test="@CDTIPAGE">
                <xsl:element name="cd-tipage">
                    <xsl:value-of select="@CDTIPAGE"/>
                </xsl:element>
            </xsl:if>

             <xsl:if test="@DSTIPAGE">
                <xsl:element name="ds-tipage">
                    <xsl:value-of select="@DSTIPAGE"/>
                </xsl:element>
            </xsl:if>

            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>