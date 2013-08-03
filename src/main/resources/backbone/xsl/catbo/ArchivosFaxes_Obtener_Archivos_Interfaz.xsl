<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
    <xsl:template match="/">
        <wrapper-resultados xsi:type="mx.com.aon.portal.util.WrapperResultados" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
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
            <item-list xsi:type="java:mx.com.aon.catbo.model.ArchivosFaxesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        <xsl:if test="@CDTIPOAR">
		            <xsl:element name="cdtipoar">
		                <xsl:value-of select="@CDTIPOAR" />
		            </xsl:element>
		        </xsl:if>		        
		        
		        <xsl:if test="@DSARCHIVO">
		            <xsl:element name="dsarchivo">
		                <xsl:value-of select="@DSARCHIVO" />
		            </xsl:element>
		        </xsl:if>
		        
		         <xsl:if test="@CDATRIBU">
                    <xsl:element name="cdatribu">
                        <xsl:value-of select="@CDATRIBU" />
                    </xsl:element>
                </xsl:if>
		        
                <xsl:if test="@DSATRIBU">
                    <xsl:element name="dsatribu">
                        <xsl:value-of select="@DSATRIBU" />
                    </xsl:element>
                      </xsl:if>    
                          
                 <xsl:if test="@OTVALOR">
                    <xsl:element name="otvalor">
                        <xsl:value-of select="@OTVALOR" />
                    </xsl:element>
                    </xsl:if>
                    
                <xsl:if test="@FEINGRESO">
                    <xsl:element name="feingreso">
                        <xsl:value-of select="@FEINGRESO" />
                    </xsl:element>
                </xsl:if>
                
                                                             
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>


  