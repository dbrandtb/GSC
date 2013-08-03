<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <pantalla-vO xsi:type="java:mx.com.aon.configurador.pantallas.model.PantallaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
      </pantalla-vO>
            
 </xsl:template>
    <xsl:template match="row">
            
           <xsl:if test="@CDPANTALLA">
                <xsl:element name="cd-pantalla">
                    <xsl:value-of select="@CDPANTALLA"/>
                </xsl:element>
            </xsl:if>
            
           <xsl:if test="@DSARCHIVO">
                <xsl:element name="ds-archivo">
                    <xsl:value-of select="@DSARCHIVO"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSARCHIVOSEC">
                <xsl:element name="ds-archivo-sec">
                    <xsl:value-of select="@DSARCHIVOSEC"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSCAMPOS">
                <xsl:element name="ds-campos">
                    <xsl:value-of select="@DSCAMPOS"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@DSLABEL">
                <xsl:element name="ds-labels">
                    <xsl:value-of select="@DSLABEL"/>
                </xsl:element>
            </xsl:if>
            
    </xsl:template>
</xsl:stylesheet>