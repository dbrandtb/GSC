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
            <item-list xsi:type="java:mx.com.aon.catbo.model.ComboArchivoVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                <xsl:if test="@CDTIPOAR">
                    <xsl:element name="cd-tipoar">
                        <xsl:value-of select="@CDTIPOAR" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSARCHIVO">
                    <xsl:element name="ds-archivo">
                        <xsl:value-of select="@DSARCHIVO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSSTATUS">
                    <xsl:element name="ds-status">
                        <xsl:value-of select="@DSSTATUS" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@INDARCHIVO">
                    <xsl:element name="ind-arhivo">
                        <xsl:value-of select="@INDARCHIVO" />
                    </xsl:element>
                </xsl:if>
                
                
            </item-list>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>






