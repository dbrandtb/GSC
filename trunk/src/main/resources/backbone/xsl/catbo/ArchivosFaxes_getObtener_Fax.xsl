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
            <item-list xsi:type="java:mx.com.aon.catbo.model.FaxesVO" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
		        
		        <xsl:if test="@NMCASO">
		            <xsl:element name="nmcaso">
		                <xsl:value-of select="@NMCASO" />
		            </xsl:element>
		        </xsl:if>		        
		        
		        <xsl:if test="@NMFAX">
		            <xsl:element name="nmfax">
		                <xsl:value-of select="@NMFAX" />
		            </xsl:element>
		        </xsl:if>
		        
		         <xsl:if test="@CDTIPOAR">
                    <xsl:element name="cdtipoar">
                        <xsl:value-of select="@CDTIPOAR" />
                    </xsl:element>
                </xsl:if>
		        
                <xsl:if test="@DSTIPOAR">
                    <xsl:element name="dstipoar">
                        <xsl:value-of select="@DSTIPOAR" />
                    </xsl:element>
                      </xsl:if>    
                          
                 <xsl:if test="@FEINGRESO">
                    <xsl:element name="feingreso">
                        <xsl:value-of select="@FEINGRESO" />
                    </xsl:element>
                    </xsl:if>
                    
                <xsl:if test="@FERECEPCION">
                    <xsl:element name="ferecepcion">
                        <xsl:value-of select="@FERECEPCION" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@NMPOLIEX">
                    <xsl:element name="nmpoliex">
                        <xsl:value-of select="@NMPOLIEX" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@CDUSUARI">
                    <xsl:element name="cdusuari">
                        <xsl:value-of select="@CDUSUARI" />
                    </xsl:element>
                </xsl:if>
                
                 <xsl:if test="@DSUSUARI">
                    <xsl:element name="dsusuari">
                        <xsl:value-of select="@DSUSUARI" />
                    </xsl:element>
                </xsl:if>
                
                <xsl:if test="@BLARCHIVO">
                    <xsl:element name="blarchivo">
                        <xsl:value-of select="@BLARCHIVO" />
                    </xsl:element>
                </xsl:if>
                
                                
            </item-list>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
