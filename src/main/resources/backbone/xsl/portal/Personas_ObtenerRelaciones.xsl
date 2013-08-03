<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

    <xsl:template match="/">
        <wrapper-resultados xsi:type="java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_msg_id']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure/outparam[@id='po_title']/@value" />
            </xsl:element>

            <xsl:apply-templates select="result/storedProcedure/outparam/rows" />

        </wrapper-resultados>
    </xsl:template>


    <xsl:template match="rows">
        <xsl:for-each select="row">
            <item-list xsi:type="java:mx.com.aon.portal.model.RelacionesPersonaVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            	
                <xsl:if test="@CDPERSON">
                    <xsl:element name="cd-person">
                        <xsl:value-of select="@CDPERSON" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDPERREL">
                    <xsl:element name="cd-per-rel">
                        <xsl:value-of select="@CDPERREL" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDROL">
                    <xsl:element name="cd-rol">
                        <xsl:value-of select="@CDROL" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@CDRELACI">
                    <xsl:element name="cd-relaci">
                        <xsl:value-of select="@CDRELACI" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSNOMBRE">
                    <xsl:element name="ds-nombre">
                        <xsl:value-of select="@DSNOMBRE" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DESCRIPL">
                    <xsl:element name="descrip">
                        <xsl:value-of select="@DESCRIPL" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSROL">
                    <xsl:element name="ds-rol">
                        <xsl:value-of select="@DSROL" />
                    </xsl:element>
                </xsl:if>
                
              
                
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
