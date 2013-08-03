<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <tatri-parametros-vO xsi:type="java:mx.com.aon.flujos.model.TatriParametrosVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />
      </tatri-parametros-vO>
         
        
            
 </xsl:template>
    <xsl:template match="row">
            <xsl:if test="@NMSITUAC">
                <xsl:element name="nmsituac">
                    <xsl:value-of select="@NMSITUAC"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@NMSUPLEM">
                <xsl:element name="nmsuplem">
                    <xsl:value-of select="@NMSUPLEM"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@STATUS">
                <xsl:element name="status">
                    <xsl:value-of select="@STATUS"/>
                </xsl:element>
            </xsl:if>
            
           
            <xsl:if test="@CDTIPSIT">
                <xsl:element name="cdtipsit">
                    <xsl:value-of select="@CDTIPSIT"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@SWFORMAT">
                <xsl:element name="swformat">
                    <xsl:value-of select="@SWFORMAT"/>
                </xsl:element>
            </xsl:if>
           
            <xsl:if test="@SWOBLIGA">
                <xsl:element name="swobliga">
                    <xsl:value-of select="@SWOBLIGA"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@NMLMAX">
                <xsl:element name="nmlmax">
                    <xsl:value-of select="@NMLMAX"/>
                </xsl:element>
            </xsl:if>

            <xsl:if test="@NMLMIN">
                <xsl:element name="nmlmin">
                    <xsl:value-of select="@NMLMIN"/>
                </xsl:element>
            </xsl:if>
            
            <xsl:if test="@OTTABVAL">
                <xsl:element name="ottabval">
                    <xsl:value-of select="@OTTABVAL"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@SWPRODUC">
                <xsl:element name="swproduc">
                    <xsl:value-of select="@SWPRODUC"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@SWSUPLEM">
                <xsl:element name="swsuplem">
                    <xsl:value-of select="@SWSUPLEM"/>
                </xsl:element>
            </xsl:if>
           <xsl:if test="@GB_SWFORMAT">
                <xsl:element name="gb-swformat">
                    <xsl:value-of select="@GB_SWFORMAT"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@CDATRIBU">
                <xsl:element name="cdatribu">
                    <xsl:value-of select="@CDATRIBU"/>
                </xsl:element>
            </xsl:if>
            <xsl:if test="@OTVALOR">
                <xsl:element name="otvalor">
                    <xsl:value-of select="@OTVALOR"/>
                </xsl:element>
            </xsl:if>
    </xsl:template>
</xsl:stylesheet>