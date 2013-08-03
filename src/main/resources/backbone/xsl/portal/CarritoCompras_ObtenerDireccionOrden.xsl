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
            <item-list xsi:type="java:mx.com.aon.portal.model.CarritoComprasDireccionOrdenVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            
            <xsl:if test="@NMORDDOM">
                <xsl:element name="nm-ord-dom">
                    <xsl:value-of select="@NMORDDOM"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CDTIPDOM">
                <xsl:element name="cd-tip-dom">
                    <xsl:value-of select="@CDTIPDOM"/>
                </xsl:element>
            </xsl:if>

           <xsl:if test="@DSTIPDOM">
                <xsl:element name="ds-tip-dom">
                    <xsl:value-of select="@DSTIPDOM"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSDOMICI">
                <xsl:element name="ds-domici">
                    <xsl:value-of select="@DSDOMICI"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@NMNUMERO">
                <xsl:element name="nm-numero">
                    <xsl:value-of select="@NMNUMERO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@NMNUMINT">
                <xsl:element name="nm-num-int">
                    <xsl:value-of select="@NMNUMINT"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@CDPAIS">
                <xsl:element name="cd-pais">
                    <xsl:value-of select="@CDPAIS"/>
                </xsl:element>
            </xsl:if>
            
              <xsl:if test="@DSPAIS">
                <xsl:element name="ds-pais">
                    <xsl:value-of select="@DSPAIS"/>
                </xsl:element>
            </xsl:if>
            
            
            <xsl:if test="@CDEDO">
                <xsl:element name="cd-edo">
                    <xsl:value-of select="@CDEDO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSEDO">
                <xsl:element name="ds-edo">
                    <xsl:value-of select="@DSEDO"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@CDMUNICI">
                <xsl:element name="cd-munici">
                    <xsl:value-of select="@CDMUNICI"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DSMUNICI">
                <xsl:element name="ds-munici">
                    <xsl:value-of select="@DSMUNICI"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@CDCOLONI">
                <xsl:element name="cd-coloni">
                    <xsl:value-of select="@CDCOLONI"/>
                </xsl:element>
            </xsl:if>
            
             <xsl:if test="@DSCOLONI">
                <xsl:element name="ds-coloni">
                    <xsl:value-of select="@DSCOLONI"/>
                </xsl:element>
            </xsl:if>
            
            
             </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>