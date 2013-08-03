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
            <item-list xsi:type="java:mx.com.aon.portal.model.CarritoComprasProductosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
             <xsl:if test="@CDCARROD">
                <xsl:element name="cd-carro-d">
                    <xsl:value-of select="@CDCARROD"/>
                </xsl:element>
            </xsl:if>	
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
            
            <xsl:if test="@CDPLAN">
                <xsl:element name="cd-plan">
                    <xsl:value-of select="@CDPLAN"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSPLAN">
                <xsl:element name="ds-plan">
                    <xsl:value-of select="@DSPLAN"/>
                </xsl:element>
            </xsl:if>
              
             <xsl:if test="@FEINICIO">
                <xsl:element name="fe-inicio">
                    <xsl:value-of select="@FEINICIO"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@FEESTADO">
                <xsl:element name="fe-estado">
                    <xsl:value-of select="@FEESTADO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@MNTOTALP">
                <xsl:element name="mn-totalp">
                    <xsl:value-of select="@MNTOTALP"/>
                </xsl:element>
            </xsl:if>
            
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>