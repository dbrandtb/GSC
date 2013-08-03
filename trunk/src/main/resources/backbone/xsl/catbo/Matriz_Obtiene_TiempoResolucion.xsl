<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
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
            <item-list xsi:type="java:mx.com.aon.catbo.model.TiemposVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        <xsl:if test="@CDMATRIZ">
		            <xsl:element name="cdmatriz">
		                <xsl:value-of select="@CDMATRIZ" />
		            </xsl:element>
		        </xsl:if>
		        <xsl:if test="@CDNIVATN">
                    <xsl:element name="cdnivatn">
                        <xsl:value-of select="@CDNIVATN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSNIVATN">
                    <xsl:element name="dsnivatn">
                        <xsl:value-of select="@DSNIVATN" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@TRESOLUCION">
                    <xsl:element name="tresolucion">
                        <xsl:value-of select="@TRESOLUCION" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@TRESUNIDAD">
                    <xsl:element name="tresunidad">
                        <xsl:value-of select="@TRESUNIDAD" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@DSTRESUNIDAD">
                    <xsl:element name="dstresunidad">
                        <xsl:value-of select="@DSTRESUNIDAD" />
                    </xsl:element>
                </xsl:if>
                
                
                <xsl:if test="@TALARMA">
                    <xsl:element name="talarma">
                        <xsl:value-of select="@TALARMA" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@TALAUNIDAD">
                    <xsl:element name="talaunidad">
                        <xsl:value-of select="@TALAUNIDAD" />
                    </xsl:element>
                </xsl:if>
                
                  <xsl:if test="@DSTALAUNIDAD">
                    <xsl:element name="dstalaunidad">
                        <xsl:value-of select="@DSTALAUNIDAD" />
                    </xsl:element>
                </xsl:if>
                
                
                <xsl:if test="@TESCALAMIENTO">
                    <xsl:element name="tescalamiento">
                        <xsl:value-of select="@TESCALAMIENTO" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@TESCAUNIDAD">
                    <xsl:element name="tescaunidad">
                        <xsl:value-of select="@TESCAUNIDAD" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSTESCAUNIDAD">
                    <xsl:element name="dstescaunidad">
                        <xsl:value-of select="@DSTESCAUNIDAD" />
                    </xsl:element>
                </xsl:if>
                                                                        
              
                
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>


