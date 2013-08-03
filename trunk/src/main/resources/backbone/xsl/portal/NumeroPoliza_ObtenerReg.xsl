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
            <item-list xsi:type="java:mx.com.aon.portal.model.NumeroPolizaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

            <xsl:if test="@CDUNIECO">
                <xsl:element name="cd-uni-eco">
                    <xsl:value-of select="@CDUNIECO"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DSUNIECO">
                <xsl:element name="ds-uni-eco">
                    <xsl:value-of select="@DSUNIECO"/>
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
            
            <xsl:if test="@CDELEMENTO">
                <xsl:element name="cd-elemento">
                    <xsl:value-of select="@CDELEMENTO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSELEMEN">
                <xsl:element name="ds-elemen">
                    <xsl:value-of select="@DSELEMEN"/>
                </xsl:element>
            </xsl:if>
                
            <xsl:if test="@INDSUFPRE">
                <xsl:element name="ind-suf-pre">
                    <xsl:value-of select="@INDSUFPRE"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSINDSUFPRE">
                <xsl:element name="ds-ind-suf-pre">
                    <xsl:value-of select="@DSINDSUFPRE"/>
                </xsl:element>
            </xsl:if>
             
            <xsl:if test="@DSSUFPRE">
                <xsl:element name="ds-suf-pre">
                    <xsl:value-of select="@DSSUFPRE"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@INDCALC">
                <xsl:element name="ind-calc">
                    <xsl:value-of select="@INDCALC"/>
                </xsl:element>
            </xsl:if>
            
              <xsl:if test="@DSINDCALC">
                <xsl:element name="ds-ind-calc">
                    <xsl:value-of select="@DSINDCALC"/>
                </xsl:element>
            </xsl:if>
            
              <xsl:if test="@NMFOLIOINI">
                <xsl:element name="nm-folio-ini">
                    <xsl:value-of select="@NMFOLIOINI"/>
                </xsl:element>
            </xsl:if>
            
               <xsl:if test="@NMFOLIOFIN">
                <xsl:element name="nm-folio-fin">
                    <xsl:value-of select="@NMFOLIOFIN"/>
                </xsl:element>
            </xsl:if>
            
              <xsl:if test="@NMFOLIOACT">
                <xsl:element name="nm-folio-act">
                    <xsl:value-of select="@NMFOLIOACT"/>
                </xsl:element>
            </xsl:if>
            
               <xsl:if test="@DSCALCULO">
                <xsl:element name="ds-calculo">
                    <xsl:value-of select="@DSCALCULO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDNUMPOL">
                <xsl:element name="cd-num-pol">
                    <xsl:value-of select="@CDNUMPOL"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDTIPPOL">
                <xsl:element name="cd-tip-pol">
                    <xsl:value-of select="@CDTIPPOL"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@SWAGRUPA">
                <xsl:element name="sw-agrupa">
                    <xsl:value-of select="@SWAGRUPA"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDEXPRES">
                <xsl:element name="cd-expres">
                    <xsl:value-of select="@CDEXPRES"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDPROCESO">
                <xsl:element name="cd-proceso">
                    <xsl:value-of select="@CDPROCESO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSPROCESO">
                <xsl:element name="ds-proceso">
                    <xsl:value-of select="@DSPROCESO"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSAGRUPA">
                <xsl:element name="ds-agrupa">
                    <xsl:value-of select="@DSAGRUPA"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@DSTIPPOL">
                <xsl:element name="ds-tip-pol">
                    <xsl:value-of select="@DSTIPPOL"/>
                </xsl:element>
            </xsl:if>
                        
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>