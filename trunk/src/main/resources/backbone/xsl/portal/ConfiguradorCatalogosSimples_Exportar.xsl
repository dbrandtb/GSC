<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet
    version = "1.0"
    xmlns:xsl = "http://www.w3.org/1999/XSL/Transform"
    xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
    xmlns:fo = "http://www.w3.org/1999/XSL/Format"
    xmlns:java = "http://xml.apache.org/xslt/java"
    exclude-result-prefixes = "java">

    <xsl:template match = "/">
        <wrapper-resultados xsi:type = "java:mx.com.aon.portal.util.WrapperResultados" xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance">
            <xsl:element name="msg-id">
                <xsl:value-of select="result/storedProcedure[@id='obtenerRegistros']/outparam[@id='pv_msg_id_o']/@value" />
            </xsl:element>
            <xsl:element name="msg">
                <xsl:value-of select="result/storedProcedure[@id='obtenerRegistros']/outparam[@id='pv_title_o']/@value" />
            </xsl:element>
            <xsl:for-each select = "result/storedProcedure[@id='obtenerRegistros']/outparam[@id='pv_registro_o']/rows/row">
                <item-list xsi:type = "java:java.util.ArrayList">
                    <xsl:for-each select = "@*">
                        <xsl:variable name = "colname" select = "name()"/>
                        <xsl:variable name = "colvalue" select = "."/>
                        <xsl:for-each select = "/result/storedProcedure[@id='obtenerCampos']/outparam[@id='pv_registro_o']/rows/row">
                         <xsl:if test = "@CDCOLUMN=$colname">
				           <!-- lista-columnas xsi:type="java:java.util.ArrayList" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"-->
                            <xsl:if test = "@CDCOLUMN=$colname">
		                        <!--xsl:element name="name">
		                          <xsl:value-of select = "$colname"/>
                                </xsl:element>
		                        <xsl:element name="value">
                                  <xsl:value-of select = "$colvalue"/>
                                </xsl:element-->
								<string xsi:type="java:java.lang.String" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
								<xsl:value-of select="$colvalue"/>
								</string>
                                
                            </xsl:if>

                           <!--/lista-columnas-->
                         </xsl:if> 
                        </xsl:for-each>
                    </xsl:for-each>
                </item-list>
            </xsl:for-each>
        </wrapper-resultados>
    </xsl:template>
</xsl:stylesheet>
